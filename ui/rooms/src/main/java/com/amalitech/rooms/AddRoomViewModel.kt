package com.amalitech.rooms

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core.R
import com.amalitech.core.util.UiText
import com.amalitech.onboarding.signup.use_case.FetchLocationsUseCase
import com.amalitech.rooms.model.Room
import com.amalitech.rooms.usecase.AddRoomUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddRoomViewModel(
    private val getLocation: FetchLocationsUseCase,
    private val addRoom: AddRoomUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(
        AddRoomUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val capacity
        get() = _uiState.value.capacity

    init {
        fetchLocations()
    }

    private fun fetchLocations() {
        viewModelScope.launch {
            val resultLocation = getLocation()
            val data = resultLocation.data
            val error = resultLocation.error
            if (data != null) {
                _uiState.update {
                    it.copy(
                        locationList = data
                    )
                }
            } else if (error != null) {
                _uiState.update {
                    it.copy(error = error)
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = UiText.StringResource(R.string.error_default_message)
                    )
                }
            }
        }
    }

    fun onRoomImages(images: List<Uri>) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                imagesList = (addRoomUiState.imagesList + images).toMutableList()
            )
        }
    }


    fun onRoomName(roomName: String) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                name = roomName
            )
        }
    }

    fun onNewRoomCapacity(value: Int) {
        _uiState.update { addRoomUiState ->
            addRoomUiState.copy(
                capacity = value
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
                features = features
            )
        }
    }

    fun onSelectedLocation(location: String) {
        _uiState.update { addRoomUiState ->
            val id = addRoomUiState.locationList?.find { it.name == location }?.id ?: -1
            addRoomUiState.copy(
                location = id
            )
        }
    }

    fun onSaveRoomClick(context: Context) {
        when {
            _uiState.value.name.isBlank() -> {
                // TODO: this is an alternative way of handling errors, and using is error / supporting text in the ui
                _uiState.update { state ->
                    state.copy(error = UiText.StringResource(R.string.name_empty))
                }
                return
            }

            _uiState.value.location == -1 -> {
                _uiState.update { state ->
                    state.copy(error = UiText.StringResource(R.string.location_empty))
                }
                return
            }

            _uiState.value.features.isBlank() -> {
                _uiState.update { state ->
                    state.copy(error = UiText.StringResource(R.string.features_empty))
                }
                return
            }

            _uiState.value.imagesList.isEmpty() -> {
                _uiState.update { state ->
                    state.copy(error = UiText.StringResource(R.string.images_empty))
                }
                return
            }

            else -> {}
        }

        viewModelScope.launch {
            val result = addRoom(
                mapRoomToDomain(
                    _uiState.value.name.trim(),
                    _uiState.value.location,
                    _uiState.value.features.trim(),
                    _uiState.value.capacity,
                    _uiState.value.imagesList
                ),
                context
            )
            if (result != null) {
                _uiState.update {
                    it.copy(error = result)
                }
            }

        }
    }

    // TODO: crate a share mapping logic/function
    private fun mapRoomToDomain(
        name: String,
        location: Int,
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

    fun onDeleteImage(uri: Uri) {
        _uiState.update { addRoomUiState ->
            val list = addRoomUiState.imagesList.toMutableList()
            list.remove(uri)
            addRoomUiState.copy(
                imagesList = list
            )
        }
    }

    fun clearSnackBar() {
        _uiState.update {
            it.copy(
                error = null
            )
        }
    }
}