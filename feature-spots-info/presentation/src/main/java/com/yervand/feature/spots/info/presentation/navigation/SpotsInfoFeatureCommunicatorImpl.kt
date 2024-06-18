package com.yervand.feature.spots.info.presentation.navigation

import android.content.Context
import android.content.Intent
import com.yervand.feature.spots.info.presentation.SpotsInfoActivity
import com.yervand.feature.spots.info.shared.SpotsInfoFeatureCommunicator
import com.yervand.feature.spots.info.shared.arg.SpotsArg
import javax.inject.Inject

internal class SpotsInfoFeatureCommunicatorImpl @Inject constructor() : SpotsInfoFeatureCommunicator {
    override fun launchFeature(context: Context, args: SpotsArg): Intent {
        return Intent(context, SpotsInfoActivity::class.java).apply {
            putExtra(SpotsInfoFeatureCommunicator.SPOTS_ARG, args)
        }
    }
}
