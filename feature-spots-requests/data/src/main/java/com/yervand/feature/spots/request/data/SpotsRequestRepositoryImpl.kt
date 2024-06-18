package com.yervand.feature.spots.request.data

import com.yervand.core.entities.Spot
import com.yervand.core.network.SpotsApi
import com.yervand.feature.spots.request.data.mapper.SpotsResponseMapper
import com.yervand.feature.spots.requests.domain.repository.SpotsRequestRepository
import javax.inject.Inject

internal class SpotsRequestRepositoryImpl @Inject constructor(
    private val spotsApi: SpotsApi,
    private val spotsResponseMapper: SpotsResponseMapper
) : SpotsRequestRepository {

    companion object {
        private const val SPOT_COUNT_PARAM = "count"
    }

    override suspend fun getSpots(spotCount: Int): List<Spot> {
        return spotsApi.getSpots(
            parameters = mapOf(
                SPOT_COUNT_PARAM to spotCount.toString()
            )
        ).let(spotsResponseMapper::map)
    }
}