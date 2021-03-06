package com.example.myapplication.features.quotes.ui.quotedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.myapplication.databinding.FragmentQuoteDetailsBinding
import com.example.myapplication.features.quotes.ui.populateWithTags
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuoteDetailsFragment : Fragment() {

    private val viewModel: QuoteDetailsViewModel by viewModels()

    private var _binding: FragmentQuoteDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuoteDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.quote.let {
            binding.quote.text = it.body
            binding.author.text = it.author
            binding.tagsLayout.populateWithTags(it.tags)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}