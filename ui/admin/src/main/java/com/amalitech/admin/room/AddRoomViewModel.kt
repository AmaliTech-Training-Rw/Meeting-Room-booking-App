package com.amalitech.admin.room

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.admin.room.usecase.AddRoom
import com.amalitech.admin.room.usecase.GetLocation
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddRoomViewModel(
    private val getLocation: GetLocation,
    private val addRoom: AddRoom
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

    fun onRoomImages(images: List<@JvmSuppressWildcards Uri>) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                imagesList = images
            )
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

    // TODO: cadet => these snack bar error messages will only show after the feature has been connected to the scaffold
    fun onSaveRoomClick() {
        when {
            _uiState.value.name.isBlank() -> {
                // TODO: this is an alternative way of handling errors, and using is error / supporting text in the ui
                updateStateWithError(true, "Name value is empty")
                SnackbarManager.showMessage(com.amalitech.core.R.string.name_empty)
                return
            }

            _uiState.value.location.isBlank() -> {
                SnackbarManager.showMessage(com.amalitech.core.R.string.location_empty)
                return
            }

            _uiState.value.features.isBlank() -> {
                SnackbarManager.showMessage(com.amalitech.core.R.string.features_empty)
                return
            }

            _uiState.value.imagesList.isEmpty() -> {
                SnackbarManager.showMessage(com.amalitech.core.R.string.images_empty)
                return
            }

            else -> updateStateWithError(false, "")
        }

        launchCatching {
            addRoom(
                mapRoomToDomain(
                    _uiState.value.name,
                    _uiState.value.location,
                    _uiState.value.features,
                    _uiState.value.capacity,
                    _uiState.value.imagesList
                )
            )
        }
    }

    private fun mapRoomToDomain(
        name: String,
        location: String,
        features: String,
        capacity: Int,
        selectImages: List<Uri>
    ): Room {
        return Room(
            name,
            capacity,
            location,
            selectImages,
            features
        )
    }

    // TODO: this is an alternative, so it can just be deleted if not needed
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