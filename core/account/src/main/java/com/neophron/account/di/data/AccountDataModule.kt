package com.neophron.account.di.data

import android.app.Application
import com.neophron.account.data.preference.AccountPreferenceImpl
import com.neophron.account.data.AccountRepositoryImpl
import com.neophron.account.data.preference.AccountPreference
import com.neophron.account.domain.repositories.AccountRepository
import com.neophron.network.base.UserTokenStore
import com.neophron.network.account.source.AccountNetworkDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import javax.inject.Singleton

@Module
class AccountDataModule {


    @Provides
    @Singleton
    fun provideAccountPreference(application: Application): AccountPreference {
        return AccountPreferenceImpl(application)
    }

    @Provides
    @Singleton
    fun provideAccountRepository(
        networkSource: AccountNetworkDataSource,
        accountPreference: AccountPreference,
        userTokenStore: UserTokenStore,
        longLiveScope: CoroutineScope
    ): AccountRepository =
        AccountRepositoryImpl(networkSource, accountPreference, userTokenStore, longLiveScope)


}