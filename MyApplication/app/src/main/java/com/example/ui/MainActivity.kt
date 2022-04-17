package com.example.ui

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.anychart.AnyChart
import com.anychart.chart.common.dataentry.DataEntry
import com.anychart.chart.common.dataentry.ValueDataEntry
import com.anychart.data.Set
import com.example.application.R
import com.example.data.DataModel
import com.example.ui.adapter.MainAdapter
import com.example.ui.loading.ProgressDialog
import com.example.util.Const
import com.example.util.ext.*
import com.example.worker.SyncWorker
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import java.util.*

class MainActivity : AppCompatActivity() {

    private var loading: ProgressDialog? = null
    private val viewModel: MainViewModel by inject()
    private val mainAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loading = loading.initLoading(this@MainActivity)
        checkLocationPermission()
        with(viewModel) {
            shouldShowData.observe(this@MainActivity, Observer {
                it?.let {
                    btn_refresh?.isEnabled = true
                    if (it.second.isNotEmpty()) {
                        rv_data?.visibility = View.VISIBLE
                        acv_graph?.visibility = View.VISIBLE
                        tv_empty?.visibility = View.GONE
                        ("*" + it.second.firstOrNull()?.disclaimer.orEmpty()).also { tv_info?.text = it }
                        tv_subtitle?.text = it.first
                        mainAdapter.submitList(it.second)
                        rv_data?.adapter = mainAdapter
                        setGraph(it.second)
                    } else {
                        rv_data?.visibility = View.GONE
                        acv_graph?.visibility = View.GONE
                        tv_empty?.visibility = View.VISIBLE
                    }
                }
            })
            shouldShowLoading.observe(this@MainActivity, Observer {
                loading?.showLoading(it)
            })
            shouldShowError.observe(this@MainActivity, Observer {
                handleCommonError(it as Throwable)
            })
        }

        btn_refresh?.setOnSingleClickListener {
            checkLocationPermission()
        }
    }

    private fun setGraph(it: List<DataModel>) {
        val line = AnyChart.line()
        val set = Set.instantiate()
        val listData: MutableList<DataEntry> = mutableListOf()
        listData.add(ValueDataEntry("00", 0))
        listData.add(ValueDataEntry("02", 0))
        listData.add(ValueDataEntry("04", 0))
        listData.add(ValueDataEntry("06", 0))
        listData.add(ValueDataEntry("08", 0))
        listData.add(ValueDataEntry("10", 0))
        listData.add(ValueDataEntry("12", 0))
        listData.add(ValueDataEntry("14", 0))
        listData.add(ValueDataEntry("16", 0))
        listData.add(ValueDataEntry("18", 0))
        listData.add(ValueDataEntry("20", 0))
        listData.add(ValueDataEntry("22", 0))
        listData.addAll(it.sortedBy { it.time?.updatedISO }.map {
            ValueDataEntry(
                it.time?.updatedISO?.toDateString(Const.APP_DAY_GRAPH),
                it.bpi?.usd?.rateFloat
            )
        })
        line.xAxis(0).labels().padding(2, 2, 2, 2)
        set.data(listData)
        val series = line.line(set.mapAs("{ x: 'x', value: 'value' }"))
        series.name(resources.getString(R.string.title_graph))
        series.stroke("{ color: '#3B8EF3', thickness: 1}")
        acv_graph.setChart(line)
    }

    private fun continueProcess() {
        viewModel.onViewLoaded()
        SyncWorker.start(this@MainActivity)
        WorkManager.getInstance(this@MainActivity)
            .getWorkInfoByIdLiveData(SyncWorker.workRequest.id)
            .observe(this, Observer { workInfo ->
                if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                    viewModel.onViewLoaded()
                }
            })
    }

    private fun checkLocationPermission() {
        btn_refresh?.isEnabled = false
        if (isLocationDisable()) {
            btn_refresh?.isEnabled = true
            showSnackbar(getString(R.string.label_gps_problem))
        } else {
            onRequestPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                requestCode = Const.ASK_PERMISSION,
                callback = {
                    onGranted {
                        continueProcess()
                    }
                    onDenied {
                        btn_refresh?.isEnabled = true
                        tv_empty?.visibility = View.VISIBLE
                        showSnackbar(getString(R.string.label_permission_denied))
                    }
                    onPermanentDenied {
                        btn_refresh?.isEnabled = true
                        tv_empty?.visibility = View.VISIBLE
                        showSnackbar(getString(R.string.label_permanent_permission_denied))
                    }
                }
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        handleRequestPermission(requestCode, permissions, grantResults)
    }
}