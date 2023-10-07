package com.dicoding.githubuser.data.retrofit

import androidx.viewbinding.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiConfig {
    companion object{
        fun getApiService(): ApiService{
            val loggingInterceptor = if (BuildConfig.DEBUG){
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
            } else {
                HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
            }
            val apiKey = com.dicoding.githubuser.BuildConfig.KEY
            val authInterceptor = Interceptor{ chain ->
                val req = chain.request()
                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", apiKey)
                    .build()
                chain.proceed(requestHeaders)

            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            val baseUrl = com.dicoding.githubuser.BuildConfig.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}