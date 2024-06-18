package com.yervand.feature.spots.requests.presentation.navigation

import android.content.Context
import android.content.Intent
import com.yervand.feature.spots.requests.presentation.SpotsRequestActivity
import com.yervand.feature.spots.requests.shared.SpotsRequestsFeatureCommunicator
import javax.inject.Inject

internal class SpotsRequestsFeatureCommunicatorImpl @Inject constructor() : SpotsRequestsFeatureCommunicator {
    override fun launchFeature(context: Context): Intent = Intent(context, SpotsRequestActivity::class.java)
}
