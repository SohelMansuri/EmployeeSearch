package com.sohel.mansuri.employeesearch.utils

import android.content.Context
import java.io.IOException
import java.nio.charset.Charset

/**
 * Created by sohelmansuri on 8/20/19.
 */
class FileUtils {
    companion object {
        /**
         * Use this method to retrieve JSON Data as a string from given file.
         *
         * @param context       The application Context.
         * @param fileName      File name to search for and retrieve JSON data from
         *
         * @return              JSON Data in string format
         */
        fun getJSONDataFromAssets(context: Context, fileName: String): String? {
            var json: String? = null
            try {
                val inputStream = context.assets.open(fileName)
                val size = inputStream.available()
                val buffer = ByteArray(size)
                inputStream.read(buffer)
                inputStream.close()
                json = String(buffer, Charset.forName("UTF-8"))
            } catch (ex: IOException) {
                ex.printStackTrace()
            }
            return json
        }
    }
}
