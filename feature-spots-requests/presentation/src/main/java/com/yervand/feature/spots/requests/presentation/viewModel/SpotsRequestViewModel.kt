package com.yervand.feature.spots.requests.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yervand.feature.spots.requests.domain.usecase.GetSpotsUseCase
import com.yervand.feature.spots.requests.presentation.UiEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpotsRequestViewModel @Inject constructor(
    private val getSpotsUseCase: GetSpotsUseCase,
    private val spotArgumentMapper: SpotArgumentMapper,
) : ViewModel(), SpotsRequestScreenCallbacks {

    private val _uiEvents = MutableSharedFlow<UiEvents>()
    internal val uiEvents = _uiEvents.asSharedFlow()

    private val _state = MutableStateFlow(SpotsRequestViewState())
    internal val state = _state.asStateFlow()

    override fun onRequestSpotsClick(spotCount: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(isLoading = true)
            }
            getSpotsUseCase(spotCount)
                .onSuccess { spots ->
                    _uiEvents.emit(
                        UiEvents.NavigateToSpotsInfo(
                            spotArgumentMapper.map(spots)
                        )
                    )
                }.onFailure {
                    _uiEvents.emit(
                        UiEvents.ShowToast(
                            it.localizedMessage ?: "Something went wrong"
                        )
                    )
                }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }
}