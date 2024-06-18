package com.yervand.feature.spots.info.shared.arg

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SpotsArg(
    val spots: List<SpotArg>
) : Parcelable

