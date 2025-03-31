package com.example.ui

import android.os.Build
import androidx.core.view.isVisible
import androidx.test.core.app.launchActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.junit.After
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.R])
class MainActivityTest {

    @Test
    fun test_emptyTextGone() {
        val scenario = launchActivity<MainActivity>()
        scenario.onActivity { activity ->
            Assert.assertEquals("Test Case: Empty Text Visible Gone", activity.tv_empty.isVisible, false)
        }
    }

    @After
    fun tearDown() {
        stopKoin()
    }
}