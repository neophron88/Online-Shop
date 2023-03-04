package com.neophron.auth.di

import com.neophron.account.domain.usecases.LogInUseCase
import com.neophron.account.domain.usecases.SignInUseCase
import com.neophron.auth.di.viewModel.ProvideLogInAssistedFactory
import com.neophron.auth.di.viewModel.ProvideSignInAssistedFactory
import dagger.Component


@AuthFeatureScope
@Component(
    dependencies = [AuthFeatureDependencies::class]
)
interface AuthFeatureComponent:ProvideSignInAssistedFactory,ProvideLogInAssistedFactory {


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