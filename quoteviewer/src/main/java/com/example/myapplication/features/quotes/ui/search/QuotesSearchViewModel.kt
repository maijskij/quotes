package com.example.myapplication.features.quotes.ui.search

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.features.quotes.data.model.Quote
import com.example.myapplication.features.quotes.domain.FavQsRepository
import com.example.myapplication.features.quotes.domain.RepositoryResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuotesSearchViewModel @Inject constructor(
    private val repository: FavQsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(State())
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    init {
        searchQuotes("")
    }

    fun onQueryChanged(query: String) {
        searchQuotes(query)
    }

    private fun searchQuotes(query: String) {
        viewModelScope.launch {

            val options = mutableMapOf<String, String>()
            if (query.isNotBlank()) {
                options["filter"] = query
            }
            when (val result = repository.getQuotesList(options)) {
                is RepositoryResource.Error -> {
                    updateErrorMessage(result.error.toString())
                    updateQuotesList(emptyList())
                }
                is RepositoryResource.Success -> {

                    if (result.data.errorMessage != null) {
                        // This way FavQs handles error codes in the 200 messages
                        updateErrorMessage(result.data.errorMessage)
                        updateQuotesList(emptyList())
                    } else if (result.data.quotes.size == 1 && result.data.quotes[0].body == FAV_QS_EMPTY_LIST) {
                        // The only way to find out that quotes are not found
                        // is to check body text of the first quote (why??!)
                        updateQuotesList(emptyList())
                    } else {
                        updateQuotesList(result.data.quotes)
                    }
                }
            }
            disableInitialLoading()
        }
    }

    fun onErrorShown() {
        updateErrorMessage(null)
    }

    private fun updateErrorMessage(errorMessage: String?) {
        _uiState.update { currentState ->
            currentState.copy(errorMessage = errorMessage)
        }
    }

    private fun updateQuotesList(quotes: List<Quote>) {
        _uiState.update { currentState ->
            currentState.copy(quotes = quotes)
        }
    }

    private fun disableInitialLoading() {
        _uiState.update { currentState ->
            currentState.copy(showInitialLoading = false)
        }
    }

    data class State(
        val errorMessage: String? = null,
        val showInitialLoading: Boolean = true,
        val quotes: List<Quote> = emptyList()
    )

    companion object {
        @VisibleForTesting
        const val FAV_QS_EMPTY_LIST = "No quotes found"
    }
}

