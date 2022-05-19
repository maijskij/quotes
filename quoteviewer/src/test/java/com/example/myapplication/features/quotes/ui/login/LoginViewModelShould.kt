package com.example.myapplication.features.quotes.ui.login

import com.example.myapplication.features.quotes.data.model.*
import com.example.myapplication.features.quotes.domain.FavQsRepository
import com.example.myapplication.features.quotes.domain.RepositoryResource
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
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelShould {

    @Mock
    private lateinit var mockedRepository: FavQsRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: LoginViewModel

    @Test
    fun `Show random quote loading state on start`(): Unit = runTest {

        viewModel = LoginViewModel(mockedRepository)
        assertThat(viewModel.randomQuoteState.first()).isInstanceOf(LoginViewModel.RandomQuoteState.Loading::class.java)
    }

    @Test
    fun `Load random quote on screen init`(): Unit = runTest {

        val quote = testQuote()
        whenever(mockedRepository.getRandomQuote()) doReturn RepositoryResource.Success(quote)

        viewModel = LoginViewModel(mockedRepository)

        verify(mockedRepository).getRandomQuote()

        val successState =
            viewModel.randomQuoteState.first() as LoginViewModel.RandomQuoteState.Success
        assertThat(successState.data).isEqualTo(quote)
    }

    @Test
    fun `Handle random quote error loading`(): Unit = runTest {

        val throwable = Throwable()
        whenever(mockedRepository.getRandomQuote()) doReturn RepositoryResource.Error(throwable)

        viewModel = LoginViewModel(mockedRepository)

        verify(mockedRepository).getRandomQuote()
        assertThat(viewModel.randomQuoteState.first()).isInstanceOf(LoginViewModel.RandomQuoteState.Error::class.java)
    }

    @Test
    fun `Login to FavQs`(): Unit = runTest {
        val login = "login"
        val password = "password"
        val favQsUser = FavQsUser(FavQsUser.User(login, password))
        val favQsSession = testFavQsUserSession()
        whenever(mockedRepository.signIn(favQsUser)) doReturn RepositoryResource.Success(
            favQsSession
        )

        viewModel = LoginViewModel(mockedRepository)

        assertThat(viewModel.loginState.first().isUserLoggedIn).isFalse

        viewModel.signIn(login, password)

        verify(mockedRepository).signIn(favQsUser)
        assertThat(viewModel.loginState.first().isUserLoggedIn).isTrue
    }

    @Test
    fun `Show error message when back-end validation failed`(): Unit = runTest {
        val login = "login"
        val password = "password"
        val favQsUser = FavQsUser(FavQsUser.User(login, password))
        val errorMessage = "Wrong password!"
        val favQsUserSession = testFavQsUserSession(message = errorMessage)
        whenever(mockedRepository.signIn(favQsUser)) doReturn RepositoryResource.Success(
            favQsUserSession
        )

        viewModel = LoginViewModel(mockedRepository)
        viewModel.signIn(login, password)

        val newState = viewModel.loginState.first()
        assertThat(newState.isUserLoggedIn).isFalse
        assertThat(newState.errorMessage).isEqualTo(errorMessage)
    }
 }