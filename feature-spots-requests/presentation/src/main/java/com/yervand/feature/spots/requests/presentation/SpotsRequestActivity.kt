package com.yervand.feature.spots.requests.presentation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.yervand.feature.spots.info.shared.SpotsInfoFeatureCommunicator
import com.yervand.feature.spots.request.presentation.databinding.ActivitySpotsRequestBinding
import com.yervand.feature.spots.requests.presentation.viewModel.SpotsRequestScreenCallbacks
import com.yervand.feature.spots.requests.presentation.viewModel.SpotsRequestViewModel
import com.yervand.feature.spots.requests.presentation.viewModel.SpotsRequestViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SpotsRequestActivity : AppCompatActivity() {

    private val viewModel by viewModels<SpotsRequestViewModel>()
    private val callbacks: SpotsRequestScreenCallbacks
        get() = viewModel

    private lateinit var binding: ActivitySpotsRequestBinding

    @Inject
    internal lateinit var spotsInfoFeatureCommunicator: SpotsInfoFeatureCommunicator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpotsRequestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        subscribeEvents()
        observeState()

        binding.requestSpotsButton.setOnClickListener {
            callbacks.onRequestSpotsClick(binding.spotCountEditText.text.toString())
        }
    }

    private fun subscribeEvents() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.uiEvents.collect(::onEvent)
            }
        }
    }

    private fun onEvent(event: UiEvents) {
        when (event) {
            is UiEvents.ShowToast -> {
                Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show()
            }

            is UiEvents.NavigateToSpotsInfo -> {
                startActivity(spotsInfoFeatureCommunicator.launchFeature(this, event.spotsArgument))
            }
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.RESUMED) {
                viewModel.state.collectLatest(::handleState)
            }
        }
    }

    private fun handleState(state: SpotsRequestViewState) {
        binding.requestSpotsButton.visibility = if (state.isLoading) View.GONE else View.VISIBLE
        binding.loadingProgressBar.visibility = if (state.isLoading) View.VISIBLE else View.GONE
    }
}