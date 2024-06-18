package com.yervand.feature.spots.requests.domain.di

import com.yervand.feature.spots.requests.domain.usecase.GetSpotsUseCase
import com.yervand.feature.spots.requests.domain.usecase.GetSpotsUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface UseCasesModule {

    @Binds
    @Singleton
    fun bindGetSpotsUseCase(impl: GetSpotsUseCaseImpl): GetSpotsUseCase
}