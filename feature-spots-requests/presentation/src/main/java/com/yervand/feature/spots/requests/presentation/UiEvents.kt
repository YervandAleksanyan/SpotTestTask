package com.yervand.feature.spots.requests.presentation

import com.yervand.feature.spots.info.shared.arg.SpotsArg

sealed interface UiEvents {
    data class ShowToast(val message: String) : UiEvents
    data class NavigateToSpotsInfo(val spotsArgument: SpotsArg) : UiEvents
}