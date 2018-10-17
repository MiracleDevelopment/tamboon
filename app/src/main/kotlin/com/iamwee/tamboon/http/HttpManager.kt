package com.iamwee.tamboon.http

import com.google.gson.GsonBuilder
import com.iamwee.tamboon.BuildConfig
import com.iamwee.tamboon.common.Config
import com.iamwee.tamboon.http.interceptor.HeaderInterceptor
import com.iamwee.tamboon.utils.Crypto
import com.iamwee.tamboon.utils.KeyGenerator
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import okhttp3.logging.HttpLoggingInterceptor.Level.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {

    private const val DEFAULT_TIMEOUT = 30L

    val tamboonService: TamboonService by lazy {
        defaultRetrofit.create(TamboonService::class.java)
    }

    val gson by lazy {
        GsonBuilder().setPrettyPrinting().create()
    }

    private val defaultRetrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(Crypto.decrypt(KeyGenerator.key, Config.baseDomain))
            .client(defaultOkHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    private val defaultOkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) BODY else NONE
            })
            .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .build()
    }

}