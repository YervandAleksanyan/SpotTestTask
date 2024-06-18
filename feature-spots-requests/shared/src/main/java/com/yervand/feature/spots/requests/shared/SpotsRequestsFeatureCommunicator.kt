package com.yervand.feature.spots.requests.shared

import android.content.Context
import android.content.Intent

interface SpotsRequestsFeatureCommunicator {
    fun launchFeature(context: Context): Intent
}