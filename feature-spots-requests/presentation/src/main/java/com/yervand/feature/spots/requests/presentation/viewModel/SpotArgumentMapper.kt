package com.yervand.feature.spots.requests.presentation.viewModel

import com.yervand.core.entities.Spot
import com.yervand.core.utils.Mapper
import com.yervand.feature.spots.info.shared.arg.SpotArg
import com.yervand.feature.spots.info.shared.arg.SpotsArg
import javax.inject.Inject

class SpotArgumentMapper @Inject constructor() : Mapper<List<Spot>, SpotsArg> {

    override fun map(from: List<Spot>): SpotsArg =
        SpotsArg(
            spots = from.map {
                SpotArg(it.x, it.y)
            }
        )
}