package com.example.myapplication.features.quotes.ui.login

import android.os.Bundle
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
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null

    private val viewModel: LoginViewModel by viewModels()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadMoreQuotes.setOnClickListener {
            val action = LoginFragmentDirections.toQuotesSearch()
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.buttonLogin.setOnClickListener {
            binding.buttonLogin.isEnabled = false
            val login = binding.editTextTextPersonName.text.toString()
            val password = binding.editTextTextPassword.text.toString()
            viewModel.signIn(login, password)
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collect { newState -> handleLoginState(newState) }
            }
        }

        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.randomQuoteState.collect { newState -> handleRandomQuoteState(newState) }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun handleRandomQuoteState(newRandomQuoteState: LoginViewModel.RandomQuoteState) {
        when (newRandomQuoteState) {
            is LoginViewModel.RandomQuoteState.Success -> {
                binding.randomQuote.text = newRandomQuoteState.data.body
                binding.randomQuoteAuthor.text = newRandomQuoteState.data.author
                binding.randomQuoteAuthorProgressbar.isVisible = false
            }
            is LoginViewModel.RandomQuoteState.Error -> {
                binding.randomQuoteAuthorProgressbar.isVisible = false
            }
            LoginViewModel.RandomQuoteState.Loading -> {
                binding.randomQuoteAuthorProgressbar.isVisible = true
            }
        }
    }

    private fun handleLoginState(newLoginState: LoginViewModel.State) {
        if (newLoginState.isUserLoggedIn) {
            binding.loginBlock.isVisible = false
            binding.loggedInBlock.isVisible = true
        } else {
            binding.loginBlock.isVisible = true
            binding.buttonLogin.isEnabled = true
            binding.loggedInBlock.isVisible = false
        }
        if (newLoginState.errorMessage != null) {
            Toast.makeText(requireActivity(), newLoginState.errorMessage, Toast.LENGTH_LONG).show()
            viewModel.errorMessageShown()
        }
    }
}