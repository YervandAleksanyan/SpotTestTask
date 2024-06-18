package com.yervand.core.network.di

import com.yervand.core.network.SpotsApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class SpotsApiModule {

    @Provides
    @Singleton
    fun provideSpotsApi(): SpotsApi {
        val httpClient = OkHttpClient.Builder()

        val client = httpClient.build()
        return Retrofit.Builder()
            .baseUrl("https://hr-challenge.dev.tapyou.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(SpotsApi::class.java)
    }
}