package com.example.myapplication.features.quotes.data.network

import com.example.myapplication.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object FavQsApiImpl {

    private const val BASE_API = "https://favqs.com/api/"

    private val authorizationInterceptor = Interceptor { chain ->
        val builder = chain.request().newBuilder()
        builder.header("Authorization", "Token token=${BuildConfig.API_KEY}")
        return@Interceptor chain.proceed(builder.build())
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(authorizationInterceptor)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_API)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    val instance: FavQsApi by lazy { retrofit.create(FavQsApi::class.java) }
}