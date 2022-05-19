package com.example.myapplication.features.quotes.data.model


data class FavQsUser(val user: User) {
    data class User(
        val login: String,
        val password: String
    )
}