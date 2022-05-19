package com.example.myapplication.features.quotes.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Quote(
    val id: String,
    val author: String,
    val body: String,
    val tags: List<String>
) : Parcelable
