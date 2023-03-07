package com.neophron.network.di

import android.app.Application
import com.neophron.network.base.UserTokenStore
import com.neophron.network.base.UserTokenStoreImpl
import com.neophron.network.base.BaseUrl
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton


@Module
internal class NetworkModule {

    private fun getMoshi(): Moshi = Moshi.Builder().build()

    @Provides
    @Singleton
    internal fun provideBaseUrl(): BaseUrl {
        return BaseUrl("https://")
    }

    @Provides
    @Singleton
    internal fun provideUserTokenStore(application: Application): UserTokenStore {
        return UserTokenStoreImpl(application)
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(baseUrl: BaseUrl): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl.url)
            .addConverterFactory(MoshiConverterFactory.create(getMoshi()))
            .build()
    }
}