package com.example.myapplication.features.quotes.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.features.quotes.data.model.FavQsUser
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
class LoginViewModel @Inject constructor(
    private val repository: FavQsRepository
) : ViewModel() {

    private val _loginState = MutableStateFlow(State())
    val loginState: StateFlow<State> = _loginState.asStateFlow()

    private val _randomQuoteState = MutableStateFlow<RandomQuoteState>(RandomQuoteState.Loading)
    val randomQuoteState: StateFlow<RandomQuoteState> = _randomQuoteState.asStateFlow()

    init {
        loadRandomQuote()
    }

    private fun loadRandomQuote() {
        viewModelScope.launch {
            when (val result = repository.getRandomQuote()) {
                is RepositoryResource.Error -> {
                    _randomQuoteState.value = RandomQuoteState.Error
                    updateErrorMessageState(result.error.toString())

                }
                is RepositoryResource.Success -> {
                    _randomQuoteState.value = RandomQuoteState.Success(result.data)
                }
            }
        }
    }

    fun signIn(login: String, password: String) {
        viewModelScope.launch {
            val favQsUser = FavQsUser(FavQsUser.User(login, password))
            when (val result = repository.signIn(favQsUser)) {
                is RepositoryResource.Error -> {
                    updateToLoggedInState(false)
                    updateErrorMessageState(result.error.toString())
                }
                is RepositoryResource.Success -> {
                    if (result.data.message.isNullOrBlank()) {
                        updateToLoggedInState(true)
                    } else {
                        updateToLoggedInState(false)
                        updateErrorMessageState(result.data.message)
                    }
                }
            }
        }
    }

    fun errorMessageShown() {
        updateErrorMessageState(null)
    }

    private fun updateErrorMessageState(errorMessage: String?) {
        _loginState.update { currentUiState ->
            currentUiState.copy(
                errorMessage = errorMessage
            )
        }
    }

    private fun updateToLoggedInState(isUserLoggedIn: Boolean) {
        _loginState.update { currentUiState ->
            currentUiState.copy(
                isUserLoggedIn = isUserLoggedIn,
            )
        }
    }


    sealed class RandomQuoteState {
        object Loading : RandomQuoteState()
        object Error : RandomQuoteState()
        class Success(val data: Quote) : RandomQuoteState()
    }

    data class State(
        val errorMessage: String? = null,
        val isUserLoggedIn: Boolean = false

    )
}