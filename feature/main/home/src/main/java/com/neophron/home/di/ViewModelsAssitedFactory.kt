package com.neophron.home.di

import com.neophron.home.presentation.ui.viewModel.HomeViewModel
import dagger.assisted.AssistedFactory


interface HomeAssistedFactoryProvider {
    fun getHomeFactory(): HomeAssistedFactory
}


@AssistedFactory
interface HomeAssistedFactory {
    fun create(): HomeViewModel
}