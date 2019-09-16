package com.miller.futurechat

import android.content.Context
import com.miller.mockretrofitinterceptor.ResourceHelper
import java.io.InputStream

class AppResourceHelper(
    private val context: Context
) : ResourceHelper {

    override fun openFile(filePath: String): InputStream {
        return context.assets.open(filePath)
    }
}