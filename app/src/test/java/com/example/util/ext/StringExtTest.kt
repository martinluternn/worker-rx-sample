package com.example.util.ext

import android.os.Build
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.stopKoin
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [Build.VERSION_CODES.R])
class StringExtTest {

    @Before
    fun setUp() {
        stopKoin()
    }

    @Test
    fun test_orNA() {
        Assert.assertEquals("Test Case 1:  orNA", "".orNA(), "N/A")
        Assert.assertEquals("Test Case 2:  orNA", null.orNA(), "N/A")
        Assert.assertEquals("Test Case 3:  orNA", "Here".orNA(), "Here")
    }

    @Test
    fun test_orZero() {
        Assert.assertEquals("Test Case 1:  orZero", "".orZero(), "0")
        Assert.assertEquals("Test Case 2:  orZero", null.orZero(), "0")
        Assert.assertSame("Test Case 3:  orZero", "Here".orZero(), "Here")
    }
}