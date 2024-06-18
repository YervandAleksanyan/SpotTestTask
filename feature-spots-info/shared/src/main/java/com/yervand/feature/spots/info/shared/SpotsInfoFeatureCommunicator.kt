package com.yervand.feature.spots.info.shared

import android.content.Context
import android.content.Intent
import com.yervand.feature.spots.info.shared.arg.SpotsArg

interface SpotsInfoFeatureCommunicator {
    fun launchFeature(context: Context, args: SpotsArg): Intent

    companion object {
        const val SPOTS_ARG = "spots_arg"
    }
}