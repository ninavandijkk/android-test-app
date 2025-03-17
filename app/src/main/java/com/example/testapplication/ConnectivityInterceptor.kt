package com.example.testapplication

import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ConnectivityInterceptor() : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        if (!InternetService.instance.isOnline()) {
            throw IOException("Did not execute request because device is not connected to a network!")
        }

        return chain.proceed(chain.request())
    }
}
