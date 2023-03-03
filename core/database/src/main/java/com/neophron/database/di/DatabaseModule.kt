package com.neophron.database.di

import android.app.Application
import com.neophron.database.OnlineShopDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class DatabaseModule {

    @Provides
    @Singleton
    internal fun provideDatabase(application: Application): OnlineShopDatabase =
        OnlineShopDatabase.getDatabase(application)

}