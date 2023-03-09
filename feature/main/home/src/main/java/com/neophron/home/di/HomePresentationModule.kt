package com.neophron.home.di

import android.app.Application
import com.neophron.home.presentation.helper.UiMapper
import com.neophron.home.presentation.helper.UiMapperImpl
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers

@Module
class HomePresentationModule {


    @Provides
    fun provideUiMapper(application: Application): UiMapper {
        return UiMapperImpl(Dispatchers.IO, application)
    }
}