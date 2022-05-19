package com.example.myapplication.features.quotes.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.myapplication.databinding.FragmentQuoteSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class QuoteSearchFragment : Fragment() {

    @Inject
    lateinit var adapter: QuotesAdapter

    private var _binding: FragmentQuoteSearchBinding? = null

    private val viewModel: QuotesSearchViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentQuoteSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { newState -> handleNewState(newState) }
            }
        }
    }

    override fun onResume() {
        super.onResume()

        binding.query.addTextChangedListener(quoteQueryChangeWatcher)
        binding.query.requestFocus()
    }

    override fun onPause() {
        super.onPause()
        binding.query.removeTextChangedListener(quoteQueryChangeWatcher)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleNewState(newState: QuotesSearchViewModel.State) {
        if (newState.errorMessage != null) {
            Toast.makeText(requireActivity(), newState.errorMessage, Toast.LENGTH_LONG)
                .show()
            viewModel.onErrorShown()
        }
        if (newState.showInitialLoading) {
            binding.searchLayout.isVisible = false
            binding.loadingLayout.isVisible = true
        } else {
            binding.searchLayout.isVisible = true
            binding.loadingLayout.isVisible = false
        }
        adapter.submitList(newState.quotes)
    }

    private fun setUpAdapter() {
        binding.recyclerView.adapter = adapter
        adapter.setOnClickListener { quote ->
            val action = QuoteSearchFragmentDirections.toQuoteDetails(quote)
            findNavController(this).navigate(action)
        }
    }

    private val quoteQueryChangeWatcher = object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            viewModel.onQueryChanged(p0.toString())
        }

        override fun afterTextChanged(p0: Editable?) {}
    }
}