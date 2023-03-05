package com.neophron.auth.di

import com.neophron.account.domain.usecases.LogInUseCase
import com.neophron.account.domain.usecases.SignInUseCase
import com.neophron.auth.di.viewModel.LogInAssistedFactoryProvider
import com.neophron.auth.di.viewModel.SignInAssistedFactoryProvider
import dagger.Component


@AuthFeatureScope
@Component(
    dependencies = [AuthFeatureDependencies::class]
)
interface AuthFeatureComponent:
    SignInAssistedFactoryProvider,
    LogInAssistedFactoryProvider {


    @Component.Builder
    interface Builder {

        fun dependencies(deps: AuthFeatureDependencies): Builder

        fun build(): AuthFeatureComponent
    }

}


interface AuthFeatureDependencies {

    fun getLoginUseCase(): LogInUseCase

    fun getSignInUseCase(): SignInUseCase
}