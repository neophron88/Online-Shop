package com.neophron.account.di.domain

import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.account.domain.usecases.*
import dagger.Module
import dagger.Provides

@Module
class AccountDomainModule {

    @Provides
    fun provideChangeAvatarUseCase(repository: AccountRepository): ChangeAvatarUseCase =
        ChangeAvatarUseCase(repository)


    @Provides
    fun provideGetAccountUseCase(repository: AccountRepository): GetAccountUseCase =
        GetAccountUseCase(repository)


    @Provides
    fun provideLogInUseCase(repository: AccountRepository): LogInUseCase =
        LogInUseCase(repository)

    @Provides
    fun provideLogOutUseCase(repository: AccountRepository): LogOutUseCase =
        LogOutUseCase(repository)

    @Provides
    fun provideSignInUseCase(repository: AccountRepository): SignInUseCase =
        SignInUseCase(repository)

}