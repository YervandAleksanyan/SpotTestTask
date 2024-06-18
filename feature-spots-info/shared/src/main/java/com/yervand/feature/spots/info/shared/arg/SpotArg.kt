package com.yervand.feature.spots.info.shared.arg

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotArg (
    val x: Float,
    val y: Float
): Parcelable