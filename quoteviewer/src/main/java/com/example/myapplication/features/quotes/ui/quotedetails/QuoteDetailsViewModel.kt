package com.example.myapplication.features.quotes.ui.quotedetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.myapplication.features.quotes.data.model.Quote
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuoteDetailsViewModel @Inject constructor(state: SavedStateHandle) : ViewModel() {

    val quote = requireNotNull(state.get<Quote>("quote"))
}