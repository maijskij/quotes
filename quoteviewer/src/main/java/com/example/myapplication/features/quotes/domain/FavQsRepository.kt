package com.example.myapplication.features.quotes.domain

import com.example.myapplication.features.quotes.data.model.*

interface FavQsRepository {
    suspend fun getRandomQuote(): RepositoryResource<Quote>

    suspend fun getQuotesList(options: Map<String, String>): RepositoryResource<QuotePageWrapper>

    suspend fun signIn(favQsUser: FavQsUser): RepositoryResource<FavQsUserSession>
}