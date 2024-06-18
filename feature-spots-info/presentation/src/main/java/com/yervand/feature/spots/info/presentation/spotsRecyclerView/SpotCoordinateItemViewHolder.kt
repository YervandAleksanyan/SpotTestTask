package com.yervand.feature.spots.info.presentation.spotsRecyclerView

import androidx.recyclerview.widget.RecyclerView
import com.yervand.feature.spots.info.presentation.databinding.SpotCordinateItemBinding

internal class SpotCoordinateItemViewHolder(
    private val binding: SpotCordinateItemBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(spot: String) {
        binding.spot.text = spot
    }
}