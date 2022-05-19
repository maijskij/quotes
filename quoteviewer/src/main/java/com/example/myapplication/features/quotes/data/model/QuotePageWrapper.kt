package com.example.myapplication.features.quotes.data.model

import com.squareup.moshi.Json


class QuotePageWrapper(
    @Json(name = "error_code")
    val errorCode: String? = null,
    @Json(name = "message")
    val errorMessage: String? = null,
    val quotes: List<Quote>
)

