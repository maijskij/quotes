package com.example.myapplication.features.quotes.ui

import android.widget.LinearLayout
import android.widget.TextView
import com.example.myapplication.R

fun LinearLayout.populateWithTags(tags: List<String>) {
    removeAllViews()
    val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(0, 0, 16, 0)
    }

    for (tag in tags.take(MAX_TAGS)) {
        val tv = TextView(context)
        tv.layoutParams = params
        tv.text = tag
        tv.setTextColor(context.getColor(R.color.purple_700))
        addView(tv)
    }
}

private const val MAX_TAGS = 3