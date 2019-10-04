package com.sohel.mansuri.employeesearch

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.sohel.mansuri.employeesearch.utils.FileUtils
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * Used for testing opening and reading JSON files from Assets directory.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class FileUtilsIT {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.sohel.mansuri.employeesearch", appContext.packageName)
    }

    @Test
    fun testOpeningFileAndReadingJSON() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val jsonData = FileUtils.getJSONDataFromAssets(appContext, "employees.json")
        assertFalse(jsonData, jsonData.isNullOrEmpty())
    }

    @Test
    fun testOpeningAndReadingMissingFile() {
        val appContext = InstrumentationRegistry.getTargetContext()
        val jsonData = FileUtils.getJSONDataFromAssets(appContext, "fake-file.json")
        assertTrue(jsonData, jsonData.isNullOrEmpty())
    }
}
