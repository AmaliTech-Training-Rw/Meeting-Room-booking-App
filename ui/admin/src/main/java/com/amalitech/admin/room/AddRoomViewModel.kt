package com.amalitech.admin.room

import androidx.lifecycle.ViewModel
import com.amalitech.admin.R
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddRoomViewModel() : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddRoomUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val capacity
        get() = _uiState.value.capacity

    fun onRoomName(roomName: String) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                name = roomName.trim()
            )
        }
    }

    fun onAddRoomCapacity() {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                capacity = capacity.plus(1)
            )
        }
    }

    fun onRemoveRoomCapacity() {
        if (capacity < 0) { // TODO: move this to validation
            SnackbarManager.showMessage(com.amalitech.core.R.string.negative_room)
        }
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                capacity = capacity.minus(1)
            )
        }
    }

    fun onSaveRoomClick() {
    }
}
