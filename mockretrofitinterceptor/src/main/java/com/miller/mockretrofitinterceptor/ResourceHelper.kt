package com.miller.mockretrofitinterceptor

import java.io.InputStream

/**
 * Created by Miller on 16/09/2019
 */

interface ResourceHelper {
    fun openFile(filePath: String): InputStream
}