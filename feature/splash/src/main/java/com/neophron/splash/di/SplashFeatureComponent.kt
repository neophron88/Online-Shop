package com.neophron.splash.di

import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.splash.di.viewModelModule.ViewModelModule
import com.neophron.splash.presentation.viewModelFactory.ViewModelFactory
import dagger.Component


@SplashFeatureScope
@Component(
    dependencies = [SplashFeatureDependencies::class],
    modules = [ViewModelModule::class]
)
interface SplashFeatureComponent {

    val viewModelFactory: ViewModelFactory

    @Component.Builder
    interface Builder {

        fun dependencies(deps: SplashFeatureDependencies): Builder

        fun build(): SplashFeatureComponent
    }

}


interface SplashFeatureDependencies {

    fun getAccountUseCase(): GetAccountUseCase

}