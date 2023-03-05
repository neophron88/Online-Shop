package com.neophron.main.di

import com.neophron.main.presentation.MainViewModel
import dagger.assisted.AssistedFactory


interface MainAssistedFactoryProvider {
    fun getMainFactory(): MainAssistedFactory
}


@AssistedFactory
interface MainAssistedFactory {
    fun create(): MainViewModel
}