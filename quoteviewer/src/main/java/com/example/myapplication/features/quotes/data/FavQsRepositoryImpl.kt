package com.example.myapplication.features.quotes.data

import com.example.myapplication.di.CoroutineDispatcherIO
import com.example.myapplication.features.quotes.data.model.FavQsUser
import com.example.myapplication.features.quotes.data.model.FavQsUserSession
import com.example.myapplication.features.quotes.data.model.Quote
import com.example.myapplication.features.quotes.data.model.QuotePageWrapper
import com.example.myapplication.features.quotes.data.network.FavQsApi
import com.example.myapplication.features.quotes.domain.FavQsRepository
import com.example.myapplication.features.quotes.domain.RepositoryResource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject

class FavQsRepositoryImpl @Inject constructor(
    private val api: FavQsApi,
    @CoroutineDispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : FavQsRepository {

    override suspend fun getRandomQuote(): RepositoryResource<Quote> = try {
        val result = withContext(dispatcherIO) { api.getQuoteOfTheDay().quote }
        RepositoryResource.Success(result)
    } catch (e: IOException) {
        RepositoryResource.Error(e)
    }

    override suspend fun getQuotesList(options: Map<String, String>): RepositoryResource<QuotePageWrapper> =
        try {
            val result = withContext(dispatcherIO) { api.getQuotesList(options) }
            RepositoryResource.Success(result)
        } catch (e: IOException) {
            RepositoryResource.Error(e)
        }

    override suspend fun signIn(favQsUser: FavQsUser): RepositoryResource<FavQsUserSession> = try {
        val result = withContext(dispatcherIO) { api.signIn(favQsUser) }
        RepositoryResource.Success(result)
    } catch (e: IOException) {
        RepositoryResource.Error(e)
    }

}