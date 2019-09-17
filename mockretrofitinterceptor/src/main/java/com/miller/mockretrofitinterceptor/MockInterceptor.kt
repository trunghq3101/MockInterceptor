package com.miller.mockretrofitinterceptor;

import okhttp3.*
import java.io.InputStream
import java.net.URI

@ExperimentalStdlibApi
class MockInterceptor(
    private val resourceHelper: ResourceHelper
) : Interceptor {

    companion object {
        const val TYPE_APP_JSON = "application/json"
        const val EXTENSION_JSON = ".json"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val filePath =
            getFilePath(request.url().uri(), getFileName(request.method(), request.url()))
        var inputStream: InputStream? = null
        return try {
            inputStream = resourceHelper.openFile(filePath)
            val byteArray = inputStream.readBytes()
            Response.Builder()
                .code(200)
                .body(ResponseBody.create(MediaType.parse(TYPE_APP_JSON), byteArray))
                .message("Success")
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", TYPE_APP_JSON)
                .build()
        } catch (e: Exception) {
            e.printStackTrace()
            Response.Builder()
                .code(400)
                .message("Error")
                .request(request)
                .protocol(Protocol.HTTP_1_0)
                .addHeader("content-type", TYPE_APP_JSON)
                .build()
        } finally {
            inputStream?.close()
        }
    }

    private fun getFileName(method: String, url: HttpUrl): String {
        val fileName = url.pathSegments()[url.pathSegments().size - 1]
        return if (fileName.isEmpty()) {
            "${method}_Index$EXTENSION_JSON"
        } else {
            "${method}_$fileName$EXTENSION_JSON"
        }
    }

    private fun getFilePath(uri: URI, fileName: String): String {
        val path = if (uri.path.lastIndexOf("/") != uri.path.length - 1) {
            uri.path.substring(0, uri.path.lastIndexOf("/") + 1)
        } else {
            uri.path
        }
        return uri.host + path + fileName
    }
}
