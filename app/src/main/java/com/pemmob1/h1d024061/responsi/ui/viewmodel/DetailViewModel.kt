package com.pemmob1.h1d024061.responsi.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob1.h1d024061.responsi.data.model.DigimonDetail
import com.pemmob1.h1d024061.responsi.data.repository.DigimonRepository
import com.pemmob1.h1d024061.responsi.ui.navigation.Routes
import com.pemmob1.h1d024061.responsi.ui.state.UiState
import com.pemmob1.h1d024061.responsi.ui.state.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailViewModel(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val repository = DigimonRepository()

    private val digimonId: Int = savedStateHandle[Routes.DIGIMON_ID] ?: 0

    private val _uiState = MutableStateFlow<UiState<DigimonDetail>>(UiState.Loading)
    val uiState: StateFlow<UiState<DigimonDetail>> = _uiState.asStateFlow()

    init {
        loadDetail()
    }

    fun loadDetail() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getDigimonDetail(digimonId))
            } catch (e: Exception) {
                UiState.Error(e.toUserMessage())
            }
        }
    }
}
