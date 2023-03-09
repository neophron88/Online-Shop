package com.neophron.profile.di

import com.neophron.profile.presentation.ProfileViewModel
import dagger.assisted.AssistedFactory


interface ProfileAssistedFactoryProvider {
    fun getProfileFactory(): ProfileAssistedFactory
}


@AssistedFactory
interface ProfileAssistedFactory {
    fun create(): ProfileViewModel
}