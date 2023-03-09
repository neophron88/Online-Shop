package com.neophron.auth.presentation.sign_in

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.neophron.auth.R
import com.neophron.auth.databinding.SignInFragmentBinding
import com.neophron.auth.di.viewModel.SignInAssistedFactoryProvider
import com.neophron.feature.contract.app.AppNavigator
import com.neophron.feature.contract.common.DependencyProvider
import com.neophron.feature.contract.common.extractDependency
import com.neophron.feature.viewModelFactory.viewModelProvider
import com.neophron.mylibrary.ktx.disableErrorWhenTyping
import com.neophron.mylibrary.ktx.fragment.findParentAs
import com.neophron.mylibrary.ktx.fragment.getStringOrNull
import com.neophron.mylibrary.ktx.fragment.viewLifeCycle
import com.neophron.mylibrary.ktx.showToast
import com.neophron.mylibrary.takeAs
import com.neophron.mylibrary.viewbinding_delegate.viewBindings

class SignInFragment : Fragment(R.layout.sign_in_fragment) {

    private val navController: NavController by viewLifeCycle { findNavController() }

    private val binding: SignInFragmentBinding by viewBindings()

    private val viewModel: SignInViewModel by viewModelProvider {
        findParentAs<DependencyProvider>()
            .extractDependency<SignInAssistedFactoryProvider>()
            .getSignInFactory()
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtonSignIn()
        setupGoToLogIn()
        setupTextInputLayoutAutoErrorDisabling()
        observeUiState()
        observeUiEvent()
    }


    private fun setupButtonSignIn() = with(binding) {
        signIn.setOnClickListener {
            viewModel.signIn(
                editFirstName.text.toString().trim(),
                editLastName.text.toString().trim(),
                editEmail.text.toString().trim(),
                editPassword.text.toString().trim(),
            )
        }
    }

    private fun setupGoToLogIn() = with(binding) {
        goToLogIn.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_logInFragment)
        }
    }

    private fun setupTextInputLayoutAutoErrorDisabling() = with(binding) {
        inputFirstName.disableErrorWhenTyping()
        inputLastName.disableErrorWhenTyping()
        inputEmail.disableErrorWhenTyping()
        inputPassword.disableErrorWhenTyping()
    }

    private fun observeUiState() = viewModel.uiState.observe(viewLifecycleOwner, ::updateUi)


    private fun updateUi(uiState: SignInUiState) = with(binding) {
        inputFirstName.isEnabled = uiState.signInNotInProgress
        inputLastName.isEnabled = uiState.signInNotInProgress
        inputEmail.isEnabled = uiState.signInNotInProgress
        inputPassword.isEnabled = uiState.signInNotInProgress

        signIn.isEnabled = uiState.signInNotInProgress

        inputFirstName.error = getStringOrNull(uiState.firstNameErrorRes)
        inputLastName.error = getStringOrNull(uiState.lastNameErrorRes)
        inputEmail.error = getStringOrNull(uiState.emailErrorRes)
        inputPassword.error = getStringOrNull(uiState.passwordErrorRes)
    }


    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        when (it) {
            is SignInUiEvent.SignInSuccess ->
                requireActivity().takeAs<AppNavigator>().navigateFromAuthToMain()
            is SignInUiEvent.ToastMessage -> showToast(it.messageRes)
        }
    }

}