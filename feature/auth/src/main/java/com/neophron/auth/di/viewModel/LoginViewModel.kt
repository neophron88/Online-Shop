package com.neophron.auth.di.viewModel

import com.neophron.auth.presentation.log_in.LogInViewModel
import dagger.assisted.AssistedFactory


interface ProvideLogInAssistedFactory {
    fun getLogInFactory(): LogInAssistedFactory
}


@AssistedFactory
interface LogInAssistedFactory {
    fun create(): LogInViewModel
}