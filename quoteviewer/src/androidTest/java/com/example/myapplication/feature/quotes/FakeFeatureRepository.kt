package com.example.myapplication.feature.quotes

import androidx.annotation.VisibleForTesting
import com.example.myapplication.feature.quotes.FakeFavQsRepository.Companion.QUOTE_AUTHOR
import com.example.myapplication.feature.quotes.FakeFavQsRepository.Companion.QUOTE_BODY
import com.example.myapplication.feature.quotes.FakeFavQsRepository.Companion.QUOTE_TAG
import com.example.myapplication.features.quotes.data.model.FavQsUser
import com.example.myapplication.features.quotes.data.model.FavQsUserSession
import com.example.myapplication.features.quotes.data.model.Quote
import com.example.myapplication.features.quotes.data.model.QuotePageWrapper
import com.example.myapplication.features.quotes.domain.FavQsRepository
import com.example.myapplication.features.quotes.domain.RepositoryResource

class FakeFavQsRepository : FavQsRepository {
    override suspend fun getRandomQuote(): RepositoryResource<Quote> =
        RepositoryResource.Success(testQuote())

    override suspend fun getQuotesList(options: Map<String, String>): RepositoryResource<QuotePageWrapper> =
        RepositoryResource.Success(QuotePageWrapper(quotes = listOf(testQuote())))

    override suspend fun signIn(favQsUser: FavQsUser): RepositoryResource<FavQsUserSession> =
        RepositoryResource.Success(testSession())


    companion object{
        @VisibleForTesting
        const val QUOTE_AUTHOR = "Denis"
        @VisibleForTesting
        const val QUOTE_BODY = "Smart quote"
        @VisibleForTesting
        const val QUOTE_TAG = "Tag  test"

    }
}

private fun testQuote() = Quote("1", QUOTE_AUTHOR, QUOTE_BODY, listOf(QUOTE_TAG))
private fun testSession() = FavQsUserSession("token", "login", "email", null, null)