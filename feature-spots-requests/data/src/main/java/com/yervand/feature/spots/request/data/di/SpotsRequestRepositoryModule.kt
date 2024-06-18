package com.yervand.feature.spots.request.data.di

import com.yervand.feature.spots.request.data.SpotsRequestRepositoryImpl
import com.yervand.feature.spots.requests.domain.repository.SpotsRequestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface SpotsRequestRepositoryModule {

    @Binds
    @Singleton
    fun bindSpotsRequestRepository(impl: SpotsRequestRepositoryImpl): SpotsRequestRepository
}