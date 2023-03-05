package com.neophron.auth.di.viewModel

import com.neophron.auth.presentation.log_in.LogInViewModel
import dagger.assisted.AssistedFactory


interface LogInAssistedFactoryProvider {
    fun getLogInFactory(): LogInAssistedFactory
}


@AssistedFactory
interface LogInAssistedFactory {
    fun create(): LogInViewModel
}