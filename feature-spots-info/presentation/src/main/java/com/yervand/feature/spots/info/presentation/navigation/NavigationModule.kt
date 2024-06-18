package com.yervand.feature.spots.info.presentation.navigation

import com.yervand.feature.spots.info.presentation.navigation.SpotsInfoFeatureCommunicatorImpl
import com.yervand.feature.spots.info.shared.SpotsInfoFeatureCommunicator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal interface NavigationModule {
    @Binds
    fun bindSpotInfoFeatureCommunicator(spotInfoFeatureCommunicator: SpotsInfoFeatureCommunicatorImpl): SpotsInfoFeatureCommunicator
}