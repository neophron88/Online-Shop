package com.neophron.network.di

import android.app.Application
import com.neophron.database.account.AccountDao
import com.neophron.network.UserToken
import com.neophron.network.UserTokenImpl
import com.neophron.network.account.source.AccountNetworkDataSource
import com.neophron.network.account.source_impl.FakeNetworkAccountDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
class NetworkSourcesModule {


    @Provides
    @Singleton
    fun provideUserToken(application: Application): UserToken {
        return UserTokenImpl(application)
    }

    @Provides
    @Singleton
    fun provideUpcomingDataSource(dao: AccountDao, userToken: UserToken): AccountNetworkDataSource {
        return FakeNetworkAccountDataSource(dao, userToken)
    }
}