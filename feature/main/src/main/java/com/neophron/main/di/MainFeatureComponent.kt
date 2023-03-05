package com.neophron.main.di

import com.neophron.account.domain.usecases.ChangeAvatarUseCase
import com.neophron.account.domain.usecases.GetAccountUseCase
import com.neophron.account.domain.usecases.LogOutUseCase
import com.neophron.profile.di.ProfileAssistedFactoryProvider
import dagger.Component


@MainFeatureScope
@Component(
    dependencies = [MainFeatureDependencies::class]
)
interface MainFeatureComponent :
    MainAssistedFactoryProvider,
    ProfileAssistedFactoryProvider {

    @Component.Builder
    interface Builder {

        fun dependencies(deps: MainFeatureDependencies): Builder

        fun build(): MainFeatureComponent
    }
}

interface MainFeatureDependencies {

    fun getAccountUseCase(): GetAccountUseCase

    fun getChangeAvatarUseCase(): ChangeAvatarUseCase

    fun getLogOutUseCase(): LogOutUseCase


}