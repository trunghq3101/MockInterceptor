package com.miller.mockretrofitinterceptor;

import okhttp3.Interceptor
import okhttp3.Response

class MockInterceptor(
    private val resourceHelper: ResourceHelper
): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val filePath = ""
           
    }
}
