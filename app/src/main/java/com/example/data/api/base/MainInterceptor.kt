package com.example.data.api.base

import com.example.data.api.ext.HEADER_AGENT
import com.example.data.api.ext.HEADER_CONTENT_TYPE
import com.example.core.NetworkUtil
import okhttp3.*
import okhttp3.Interceptor

class MainInterceptor(
    private val networkUtil: NetworkUtil
) : Interceptor {

    override fun intercept(
        chain: Interceptor.Chain
    ): Response {
        if (networkUtil.isOnline()) {
            val response = chain.proceed(addHeader(chain))
            when (response.code()) {
                204 -> {
                    val noContent = ResponseBody.create(MediaType.get("text/plain"), "")
                    return response
                        .newBuilder()
                        .code(200)
                        .body(noContent)
                        .build()
                }
            }
            return response
        } else {
            throw NoInternetException()
        }
    }

    private fun addHeader(chain: Interceptor.Chain): Request {
        val builder = chain.request().newBuilder()

        builder.addHeader(HEADER_CONTENT_TYPE.first, HEADER_CONTENT_TYPE.second)
        builder.addHeader(HEADER_AGENT.first, HEADER_AGENT.second)

        return builder.build()
    }
}