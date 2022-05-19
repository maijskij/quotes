package com.example.myapplication.features.quotes.ui.search

import com.example.myapplication.features.quotes.data.model.Quote
import com.example.myapplication.features.quotes.data.model.testFilter
import com.example.myapplication.features.quotes.data.model.testQuotePageWrapper
import com.example.myapplication.features.quotes.domain.FavQsRepository
import com.example.myapplication.features.quotes.domain.RepositoryResource
import com.example.myapplication.features.quotes.ui.search.QuotesSearchViewModel.Companion.FAV_QS_EMPTY_LIST
import com.example.myapplication.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.any
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class QuotesSearchViewModelShould {

    @Mock
    private lateinit var mockedRepository: FavQsRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: QuotesSearchViewModel


    @Test
    fun `Load quotes with initial empty filter`(): Unit = runTest {

        val quoteWrapper = testQuotePageWrapper()
        whenever(mockedRepository.getQuotesList(testFilter())) doReturn RepositoryResource.Success(
            quoteWrapper
        )
        viewModel = QuotesSearchViewModel(mockedRepository)

        verify(mockedRepository).getQuotesList(testFilter())
        val state = viewModel.uiState.first()
        assertThat(state.quotes).isEqualTo(quoteWrapper.quotes)
        assertThat(state.showInitialLoading).isFalse
        assertThat(state.errorMessage).isEqualTo(null)
    }

    @Test
    fun `Load quotes with filter`(): Unit = runTest {

        val newQuery = "funny"
        val quoteWrapper = testQuotePageWrapper()
        whenever(mockedRepository.getQuotesList(testFilter(newQuery))) doReturn RepositoryResource.Success(
            quoteWrapper
        )

        viewModel = QuotesSearchViewModel(mockedRepository)
        viewModel.onQueryChanged(newQuery)

        verify(mockedRepository).getQuotesList(testFilter(newQuery))
        val state = viewModel.uiState.first()
        assertThat(state.quotes).isEqualTo(quoteWrapper.quotes)
        assertThat(state.showInitialLoading).isFalse
        assertThat(state.errorMessage).isEqualTo(null)
    }

    @Test
    fun `Show empty list, when FavQs returns error text inside quote`(): Unit = runTest {

        val quoteWrapper = testQuotePageWrapper(quoteBodyText = FAV_QS_EMPTY_LIST)
        whenever(mockedRepository.getQuotesList(any())) doReturn RepositoryResource.Success(quoteWrapper)
        viewModel = QuotesSearchViewModel(mockedRepository)

        val state = viewModel.uiState.first()
        assertThat(state.quotes).isEqualTo(emptyList<Quote>())
        assertThat(state.showInitialLoading).isFalse
        assertThat(state.errorMessage).isEqualTo(null)
    }

    @Test
    fun `Handle validation error from FavQs`(): Unit = runTest {

        val errorMessage = "Something went wrong"
        val quoteWrapper = testQuotePageWrapper(errorMessage = errorMessage)
        whenever(mockedRepository.getQuotesList(any())) doReturn RepositoryResource.Success(quoteWrapper)
        viewModel = QuotesSearchViewModel(mockedRepository)

        val state = viewModel.uiState.first()
        assertThat(state.errorMessage).isEqualTo(errorMessage)

        viewModel.onErrorShown()
        assertThat( viewModel.uiState.first().errorMessage).isNull()
    }
}
