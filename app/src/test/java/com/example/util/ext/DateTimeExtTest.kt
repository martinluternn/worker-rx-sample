package com.example.util.ext

import android.os.Build
import com.example.util.Const
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.R])
class DateTimeExtTest {

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun test_toDateString() {
        Assert.assertNotNull(
            "Test Case 2: Test toDateString",
            "".toDateString(Const.RFC3339_DATE_TIME_FORMAT)
        )
    }
}