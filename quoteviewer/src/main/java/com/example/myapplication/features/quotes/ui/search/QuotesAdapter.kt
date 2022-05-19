package com.example.myapplication.features.quotes.ui.search

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.features.quotes.data.model.Quote
import com.example.myapplication.features.quotes.ui.populateWithTags
import javax.inject.Inject


class QuotesAdapter @Inject constructor() :
    ListAdapter<Quote, QuotesAdapter.QuotesAdapterHolder>(DIFF_CALLBACK) {

    private var onClick: ((Quote) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesAdapterHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.quote_row_item, parent, false)

        return QuotesAdapterHolder(view) { position -> onClick?.invoke(getItem(position)) }
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: QuotesAdapterHolder, position: Int) {
        val quote = getItem(position)
        holder.textView.text = quote.body
        holder.authorView.text = quote.author

        holder.tagsView.populateWithTags(quote.tags)
    }

    fun setOnClickListener(onClickHandler: (Quote) -> Unit) {
        onClick = onClickHandler
    }

    class QuotesAdapterHolder(view: View, private val onClick: (Int) -> Unit) :
        RecyclerView.ViewHolder(view) {
        val textView: TextView = view.findViewById(R.id.quote)
        val authorView: TextView = view.findViewById(R.id.author)
        val tagsView: LinearLayout = view.findViewById(R.id.tagsLayout)

        init {
            view.setOnClickListener { onClick(adapterPosition) }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Quote>() {
            override fun areItemsTheSame(oldItem: Quote, newItem: Quote) =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Quote, newItem: Quote) =
                oldItem.body == newItem.body && oldItem.author == newItem.author
        }
    }
}

