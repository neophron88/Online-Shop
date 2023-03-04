package com.neophron.network.di

import com.neophron.database.account.AccountDao
import com.neophron.network.UserTokenStore
import com.neophron.network.account.source.AccountNetworkDataSource
import com.neophron.network.account.source_impl.FakeNetworkAccountDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class NetworkSourcesModule {

    @Provides
    @Singleton
    fun provideAccountNetworkDataSource(
        dao: AccountDao,
        userTokenStore: UserTokenStore
    ): AccountNetworkDataSource {
        return FakeNetworkAccountDataSource(dao, userTokenStore)
    }
}