package com.example.myapplication.features.quotes.data.model

fun testQuote(quoteBodyText: String? = null) =
    Quote("1", "Author", quoteBodyText ?: "Quote", listOf("Tag"))

fun testQuoteList(quoteBodyText: String? = null) = listOf(testQuote(quoteBodyText))

fun testQuotePageWrapper(
    quoteBodyText: String? = null,
    errorCode: String? = null,
    errorMessage: String? = null
) =
    QuotePageWrapper(errorCode, errorMessage, testQuoteList(quoteBodyText))

fun testFavQsUserSession(error_code: String? = null, message: String? = null) =
    FavQsUserSession("token", "login", "email", error_code, message)

fun testFilter(query: String? = null): Map<String, String> {
    val options = mutableMapOf<String, String>()
    if (!query.isNullOrEmpty()) {
        options["filter"] = query
    }

    return options
}