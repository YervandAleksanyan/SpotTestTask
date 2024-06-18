package com.yervand.feature.spots.requests.domain.repository

import com.yervand.core.entities.Spot

interface SpotsRequestRepository {

    suspend fun getSpots(spotCount: Int): List<Spot>
}