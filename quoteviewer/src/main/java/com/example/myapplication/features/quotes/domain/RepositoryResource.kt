package com.example.myapplication.features.quotes.domain

sealed class RepositoryResource<T> {
    class Success<T>(val data: T) : RepositoryResource<T>()
    class Error<T>(val error: Throwable) : RepositoryResource<T>()
}