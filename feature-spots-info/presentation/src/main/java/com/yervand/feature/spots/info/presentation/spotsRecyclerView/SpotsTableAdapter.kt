package com.yervand.feature.spots.info.presentation.spotsRecyclerView

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.yervand.core.entities.Spot
import com.yervand.feature.spots.info.presentation.databinding.SpotCordinateItemBinding


internal class SpotsTableAdapter(
    private val spots: List<Spot>,
    private val context: Context
) : RecyclerView.Adapter<SpotCoordinateItemViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SpotCoordinateItemViewHolder {
        return SpotCoordinateItemViewHolder(
            SpotCordinateItemBinding.inflate(
                LayoutInflater.from(
                    context
                ), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: SpotCoordinateItemViewHolder, position: Int) {
        holder.bind(
            spot = when (position) {
                0 -> "X"
                1 -> "Y"
                else -> {
                    val spot = spots[(position - 2) / 2]
                    if (position % 2 == 0) {
                        spot.x.toString()
                    } else {
                        spot.y.toString()
                    }
                }
            }
        )
    }

    override fun getItemCount(): Int = spots.size * 2 + 2

    override fun getItemViewType(position: Int): Int =
        if (position <= 2) {
            ITEM_COORDINATE_TITLE
        } else {
            ITEM_SPOT_COORDINATE
        }

    companion object {
        const val ITEM_SPOT_COORDINATE = 0
        const val ITEM_COORDINATE_TITLE = 1
    }
}