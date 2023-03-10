package com.neophron.auth.di.viewModel

import com.neophron.auth.presentation.sign_in.SignInViewModel
import dagger.assisted.AssistedFactory

interface SignInAssistedFactoryProvider {
    fun getSignInFactory(): SignInAssistedFactory
}


@AssistedFactory
interface SignInAssistedFactory {
    fun create(): SignInViewModel
}