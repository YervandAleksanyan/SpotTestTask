package com.yervand.feature.spots.info.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import com.yervand.core.entities.Spot
import com.yervand.feature.spots.info.presentation.databinding.ActivitySpotsInfoBinding
import com.yervand.feature.spots.info.presentation.spotsRecyclerView.SpotsTableAdapter
import com.yervand.feature.spots.info.shared.SpotsInfoFeatureCommunicator
import com.yervand.feature.spots.info.shared.arg.SpotsArg

class SpotsInfoActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpotsInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpotsInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initSpotsViews(getArgs())
        initListeners()
    }

    private fun getArgs(): List<Spot> {
        val spotsArg = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(
                SpotsInfoFeatureCommunicator.SPOTS_ARG,
                SpotsArg::class.java
            )
        } else {
            intent.getParcelableExtra(SpotsInfoFeatureCommunicator.SPOTS_ARG)
        } ?: SpotsArg(emptyList())

        val spots = spotsArg.spots.map {
            Spot(it.x, it.y)
        }
        return spots
    }

    private fun initListeners() {
        binding.graphButton.setOnClickListener {
            binding.spotsGraph.isVisible = true
            binding.spotsTable.isVisible = false
        }
        binding.tableButton.setOnClickListener {
            binding.spotsGraph.isVisible = false
            binding.spotsTable.isVisible = true
        }
    }

    private fun initSpotsViews(spots: List<Spot>) {
        binding.spotsGraph.setSpots(spots = spots)
        binding.spotsTable.adapter = SpotsTableAdapter(spots, this)
        binding.spotsTable.layoutManager = GridLayoutManager(this, SPOT_TABLE_SPAN_COUNT)
    }

    companion object {
        private const val SPOT_TABLE_SPAN_COUNT = 2
    }
}