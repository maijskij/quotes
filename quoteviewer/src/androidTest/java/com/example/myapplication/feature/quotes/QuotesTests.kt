package com.example.myapplication.feature.quotes

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.example.myapplication.R
import com.example.myapplication.features.quotes.di.QuotesModule
import com.example.myapplication.features.quotes.domain.FavQsRepository
import com.example.myapplication.features.quotes.ui.QuoteMainActivity
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@UninstallModules(QuotesModule::class)
@HiltAndroidTest
class QuotesTests {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @BindValue
    @JvmField
    val featureRepository: FavQsRepository = FakeFavQsRepository()

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun showInitialScreen() {
        ActivityScenario.launch(QuoteMainActivity::class.java)

        onView(withId(R.id.random_quote))
            .check(matches(withText(FakeFavQsRepository.QUOTE_BODY)))

        onView(withId(R.id.random_quote_author))
            .check(matches(withText(FakeFavQsRepository.QUOTE_AUTHOR)))
    }

    @Test
    fun logInToFavQs() {
        ActivityScenario.launch(QuoteMainActivity::class.java)

        onView(withId(R.id.editTextTextPersonName)).perform(replaceText("Login"))
        onView(withId(R.id.editTextTextPassword)).perform(replaceText("Password"))
        onView(withId(R.id.buttonLogin)).perform(click())

        onView(withText(R.string.you_are_logged_in)).check(matches(isDisplayed()))
    }

    @Test
    fun viewQuotesListAndDetails() {
        ActivityScenario.launch(QuoteMainActivity::class.java)

        onView(withId(R.id.load_more_quotes)).perform(click())

        onView(withId(R.id.quote))
            .check(matches(withText(FakeFavQsRepository.QUOTE_BODY)))
        onView(withId(R.id.author))
            .check(matches(withText(FakeFavQsRepository.QUOTE_AUTHOR)))
        onView(withText(FakeFavQsRepository.QUOTE_TAG))
            .check(matches(isDisplayed()))

        onView(withText(FakeFavQsRepository.QUOTE_BODY)).perform(click())

        onView(withId(R.id.quote))
            .check(matches(withText(FakeFavQsRepository.QUOTE_BODY)))
        onView(withId(R.id.author))
            .check(matches(withText(FakeFavQsRepository.QUOTE_AUTHOR)))
        onView(withText(FakeFavQsRepository.QUOTE_TAG))
            .check(matches(isDisplayed()))
    }
}