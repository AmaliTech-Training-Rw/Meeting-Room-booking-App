package com.amalitech.admin.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.admin.room.usecase.GetLocation
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddRoomViewModel(
    private val getLocation: GetLocation
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddRoomUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val capacity
        get() = _uiState.value.capacity

    init {
        launchCatching {
            getLocation.invoke().collect {
                _uiState.update { addRoomUiState ->
                    addRoomUiState.copy(
                        locationList = it
                    )
                }
            }
        }
    }

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
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                capacity = capacity.minus(1)
            )
        }
    }

    fun onFeatures(features: String) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                features = features.trim()
            )
        }
    }

    fun onSelectedLocation(location: String) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                location = location.trim()
            )
        }
    }

    fun onSaveRoomClick() {
        when {
            _uiState.value.name.isBlank() -> {
                // TODO: this is an alternative way of handling errors, and using is error / supporting text in the ui
                updateStateWithError(true, "Name value is empty")
                SnackbarManager
                    .showMessage(SnackbarMessage.StringSnackbar("Name value is empty"))
                return
            }

            _uiState.value.location.isBlank() -> {
                SnackbarManager
                    .showMessage(SnackbarMessage.StringSnackbar("Location value is empty"))
                return
            }

            _uiState.value.features.isBlank() -> {
                SnackbarManager
                    .showMessage(SnackbarMessage.StringSnackbar("Features value is empty"))
                return
            }

            else -> updateStateWithError(false, "")
        }

        launchCatching {

        }
    }

    private fun updateStateWithError(isError: Boolean, message: String) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                error = Pair(isError, message)
            )
        }
    }

    // TODO: ideally, this method should come from a share vm
    fun launchCatching(snackbar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackbar) {
                    SnackbarManager.showMessage(throwable.toSnackbarMessage())
                }
            },
            block = block
        )
}