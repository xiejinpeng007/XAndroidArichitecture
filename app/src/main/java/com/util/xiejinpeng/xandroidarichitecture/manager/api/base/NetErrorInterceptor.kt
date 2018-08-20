package com.util.xiejinpeng.xandroidarichitecture.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response
import org.simpleframework.xml.core.Persister
import java.io.IOException
import java.nio.charset.Charset


class NetErrorInterceptor(private val simpleXmlPersister: Persister) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain == null)
            return null

        val response = chain.proceed(chain.request())

        val responseBody = response.body()

        val contentType = responseBody!!.contentType()

        //TODO check contentType logic
//        if (contentType != null && !contentType.toString().contains("text/xml"))
//            return response

        val contentLength = responseBody.contentLength()

        val source = responseBody.source()
        try {
            source.request(java.lang.Long.MAX_VALUE)
        } catch (e: IOException) {
            return response
        }

        val buffer = source.buffer()

        var charset: Charset? = Charset.forName("UTF-8")
        if (contentType != null) {
            charset = contentType.charset(charset)
        }

        //TODO check the response by API Status Code

//        if (contentLength != 0L) {
//
//            var model: ServerErrorModel? = null
//            try {
//                val data = buffer.clone().readString(charset!!)
//
//                model = simpleXmlPersister.read(ServerErrorModel::class.java, data)
//            } catch (e: Exception) {
//
//            }
//            if (model != null && model.result != 1) {
//                throw ServerErrorException(model)
//            }
//
//        }

        return response
    }
}

//TODO define the API error response model
class ServerErrorException(): RuntimeException()

