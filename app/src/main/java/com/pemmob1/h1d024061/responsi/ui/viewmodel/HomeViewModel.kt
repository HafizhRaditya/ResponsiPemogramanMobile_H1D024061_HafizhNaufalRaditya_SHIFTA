package com.pemmob1.h1d024061.responsi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pemmob1.h1d024061.responsi.data.model.Digimon
import com.pemmob1.h1d024061.responsi.data.repository.DigimonRepository
import com.pemmob1.h1d024061.responsi.ui.state.UiState
import com.pemmob1.h1d024061.responsi.ui.state.toUserMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val repository = DigimonRepository()

    private val _uiState = MutableStateFlow<UiState<List<Digimon>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Digimon>>> = _uiState.asStateFlow()

    init {
        loadDigimon()
    }

    fun loadDigimon() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = try {
                UiState.Success(repository.getDigimonList())
            } catch (e: Exception) {
                UiState.Error(e.toUserMessage())
            }
        }
    }
}
