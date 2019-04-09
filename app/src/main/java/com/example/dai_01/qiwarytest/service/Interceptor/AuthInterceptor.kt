package com.example.dai_01.qiwarytest.service.Interceptor

import android.content.SharedPreferences
import com.example.dai_01.qiwarytest.extension.get
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class AuthInterceptor(val pref: SharedPreferences) : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        if (pref.get("token") == null) return chain.proceed(original)

        val builder = original.newBuilder().header("Authorization", "Bearer ${pref.get("token")}")
        val request = builder.build()
        return chain.proceed(request)
    }

}