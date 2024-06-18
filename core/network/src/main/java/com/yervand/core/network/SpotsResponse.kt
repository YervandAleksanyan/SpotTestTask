package com.yervand.core.network

import com.google.gson.annotations.SerializedName

data class SpotsResponse(
    @SerializedName("points") val spots: List<Spot>
)

data class Spot(
    @SerializedName("x") val x: Float,
    @SerializedName("y") val y: Float
)