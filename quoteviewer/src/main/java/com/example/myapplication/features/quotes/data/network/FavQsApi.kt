package com.example.myapplication.features.quotes.data.network

import com.example.myapplication.features.quotes.data.model.FavQsUser
import com.example.myapplication.features.quotes.data.model.FavQsUserSession
import com.example.myapplication.features.quotes.data.model.QuotePageWrapper
import com.example.myapplication.features.quotes.data.model.QuoteWrapper
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface FavQsApi {
    @GET("qotd")
    suspend fun getQuoteOfTheDay(): QuoteWrapper

    @GET("quotes")
    suspend fun getQuotesList(@QueryMap(encoded = true) options: Map<String, String>): QuotePageWrapper

    @POST("session")
    suspend fun signIn(@Body user: FavQsUser): FavQsUserSession

}
