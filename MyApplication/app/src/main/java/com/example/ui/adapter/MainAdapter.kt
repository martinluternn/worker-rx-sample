package com.example.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.application.R
import com.example.data.DataModel
import com.example.util.Const
import com.example.util.ext.DiffUtilCallbackEquals
import com.example.util.ext.orNA
import com.example.util.ext.orZero
import com.example.util.ext.toDateString
import kotlinx.android.synthetic.main.view_holder_data.view.*

class MainAdapter :
    ListAdapter<DataModel, MainAdapter.ContentViewHolder>(DiffUtilCallbackEquals()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.view_holder_data, parent, false)
        return ContentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        val item = getItem(position)
        holder.bindView(item)
    }

    class ContentViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindView(entity: DataModel) {
            ("Time: " + (entity.time?.updatedISO?.toDateString(Const.APP_DAY_ITEM)
                .orNA())).also { view.tv_time?.text = it }
            ("USD " + (entity.bpi?.usd?.rate.orZero())).also { view.tv_type?.text = it }
            ("Longitude: " + (entity.long.orNA())).also { view.tv_longitude?.text = it }
            ("Latitude: " + (entity.lat.orNA())).also { view.tv_latitude?.text = it }
        }
    }
}