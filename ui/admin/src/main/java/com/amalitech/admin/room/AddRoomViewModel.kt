package com.amalitech.admin.room

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddRoomViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddRoomUiState()
    )
    val uiState = _uiState.asStateFlow()

    fun onRoomName(roomName: String) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                name = roomName.trim()
            )
        }
    }

    fun onSaveRoomClick() {
    }
}
