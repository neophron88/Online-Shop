package com.neophron.auth.presentation.log_in

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.neophron.auth.R
import com.neophron.auth.databinding.LogInFragmentBinding
import com.neophron.auth.di.viewModel.ProvideLogInAssistedFactory
import com.neophron.feature.contract.DependencyProvider
import com.neophron.feature.contract.extractDependency
import com.neophron.feature.contract.viewModelProvider
import com.neophron.mylibrary.ktx.disableErrorWhenTyping
import com.neophron.mylibrary.ktx.fragment.findParentAs
import com.neophron.mylibrary.ktx.fragment.getStringOrNull
import com.neophron.mylibrary.ktx.fragment.viewLifeCycle
import com.neophron.mylibrary.ktx.showToast
import com.neophron.mylibrary.viewbinding_delegate.viewBindings

class LogInFragment : Fragment(R.layout.log_in_fragment) {

    private val navController: NavController by viewLifeCycle { findNavController() }

    private val binding: LogInFragmentBinding by viewBindings()

    private val viewModel: LogInViewModel by viewModelProvider {
        findParentAs<DependencyProvider>()
            .extractDependency<ProvideLogInAssistedFactory>()
            .getLogInFactory()
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonLogIn()
        setupTextInputLayoutAutoErrorDisabling()
        observeUiState()
        observeUiEvent()
    }

    private fun setupButtonLogIn() = with(binding) {
        logIn.setOnClickListener {
            viewModel.logIn(editEmail.text.toString().trim(), editPassword.text.toString().trim())
        }
    }

    private fun setupTextInputLayoutAutoErrorDisabling() = with(binding) {
        inputEmail.disableErrorWhenTyping()
        inputPassword.disableErrorWhenTyping()
    }

    private fun observeUiState() = viewModel.uiState.observe(viewLifecycleOwner, ::updateUi)


    private fun updateUi(uiState: LogInUiState) = with(binding) {
        inputEmail.isEnabled = uiState.logInNotInProgress
        inputPassword.isEnabled = uiState.logInNotInProgress
        logIn.isEnabled = uiState.logInNotInProgress

        inputEmail.error = getStringOrNull(uiState.emailErrorRes)
        inputPassword.error = getStringOrNull(uiState.passwordErrorRes)
    }


    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        when (it) {
            is LogInUiEvent.LogInSuccess -> {
                Log.d("it0088", "observeUiEvent: success login")
            }
            is LogInUiEvent.ToastMessage -> showToast(it.messageRes)
        }
    }

}