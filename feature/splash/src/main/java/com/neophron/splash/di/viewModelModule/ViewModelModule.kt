package com.neophron.splash.di.viewModelModule

import androidx.lifecycle.ViewModel
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.splash.presentation.SplashViewModel
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap

@Module
class ViewModelModule {

    @IntoMap
    @ViewModelKey(SplashViewModel::class)
    @Provides
    fun bindSplashViewModel(getAccountUseCase: GetAccountUseCase): ViewModel =
        SplashViewModel(getAccountUseCase)


}