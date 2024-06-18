package com.yervand.feature.spots.requests.presentation.navigation

import com.yervand.feature.spots.requests.shared.SpotsRequestsFeatureCommunicator
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
internal interface NavigationModule {
    @Binds
    fun bindSpotsRequestsFeatureCommunicator(spotsRequestsFeatureCommunicator: SpotsRequestsFeatureCommunicatorImpl): SpotsRequestsFeatureCommunicator
}