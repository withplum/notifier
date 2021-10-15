package com.withplum.notifier.github

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit


private const val CONNECT_TIMEOUT = 30L
private const val READ_TIMEOUT = 30L
private const val WRITE_TIMEOUT = 30L

class ApiClient(private val apiBaseURL: String = "https://api.github.com") {

    private val okHttpClient by lazy { provideOkHttpClient().build() }

    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(apiBaseURL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
        writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
        addLoggingTools()
    }

    private fun OkHttpClient.Builder.addLoggingTools(): OkHttpClient.Builder = this.apply {
        val loggingInterceptor = HttpLoggingInterceptor { println(it) }.apply { level = HttpLoggingInterceptor.Level.BODY }
        addInterceptor(loggingInterceptor)
    }
}
