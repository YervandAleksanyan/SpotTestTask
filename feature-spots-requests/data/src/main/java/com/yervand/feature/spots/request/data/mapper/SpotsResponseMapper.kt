package com.yervand.feature.spots.request.data.mapper

import com.yervand.core.entities.Spot
import com.yervand.core.network.SpotsResponse
import com.yervand.core.utils.Mapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class SpotsResponseMapper @Inject constructor() : Mapper<SpotsResponse, List<Spot>> {

    override fun map(from: SpotsResponse): List<Spot> =
        from.spots.map {
            Spot(it.x, it.y)
        }
}