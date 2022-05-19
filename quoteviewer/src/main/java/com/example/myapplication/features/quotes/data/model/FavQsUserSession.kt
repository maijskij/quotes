package com.example.myapplication.features.quotes.data.model

import com.squareup.moshi.Json

data class FavQsUserSession(
    @Json(name = "User-Token")
    val token: String,
    val login: String,
    val email: String,
    val error_code: String?,
    val message: String?
)