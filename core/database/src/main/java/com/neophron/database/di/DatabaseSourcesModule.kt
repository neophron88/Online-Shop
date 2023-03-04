package com.neophron.database.di

import com.neophron.database.OnlineShopDatabase
import com.neophron.database.account.AccountDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [DatabaseModule::class])
class DatabaseSourcesModule {

    @Provides
    @Singleton
    fun provideAccountDao(database: OnlineShopDatabase): AccountDao =
        database.getAccountDao()
}