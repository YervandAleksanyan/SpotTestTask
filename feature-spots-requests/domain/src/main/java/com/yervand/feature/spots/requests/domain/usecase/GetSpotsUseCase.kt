package com.yervand.feature.spots.requests.domain.usecase

import com.yervand.core.entities.Spot
import com.yervand.feature.spots.requests.domain.repository.SpotsRequestRepository
import javax.inject.Inject

interface GetSpotsUseCase {
    suspend operator fun invoke(spotCount: String): Result<List<Spot>>
}

internal class GetSpotsUseCaseImpl @Inject constructor(
    private val spotsRequestRepository: SpotsRequestRepository
) : GetSpotsUseCase {

    override suspend operator fun invoke(spotCount: String): Result<List<Spot>> =
        if(spotCount.isEmpty()) {
            Result.failure(IllegalArgumentException("Spot count cannot be empty"))
        } else {
            runCatching {
                spotsRequestRepository.getSpots(spotCount = spotCount.toInt())
            }
        }
}