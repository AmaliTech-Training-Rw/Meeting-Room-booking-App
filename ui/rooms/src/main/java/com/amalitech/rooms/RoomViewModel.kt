package com.amalitech.rooms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.core_ui.util.UiState
import com.amalitech.rooms.usecase.RoomUseCaseWrapper
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomViewModel(
    private val useCaseWrapper: RoomUseCaseWrapper
) : BaseViewModel<List<Room>>() {

    init {
        fetchRooms()
    }

    private val _searchQuery = mutableStateOf("")
    val searchQuery: State<String> = _searchQuery
    private var roomsCopy: List<Room> = emptyList()

    internal fun fetchRooms() {
        if (job?.isActive == true)
            return
        viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val response = useCaseWrapper.fetchRoomsUseCase()
            if (response.data != null) {
                response.data?.let {
                    roomsCopy = it
                    _uiStateFlow.update {
                        UiState.Success(roomsCopy)
                    }
                }
            } else if (response.error != null) {
                _uiStateFlow.update {
                    UiState.Error(response.error)
                }
            } else {
                _uiStateFlow.update {
                    UiState.Error(UiText.StringResource(com.amalitech.core.R.string.error_default_message))
                }
            }
        }
    }

    fun deleteRoom(room: Room) {
        viewModelScope.launch {
            when (val result = useCaseWrapper.deleteRoomUseCase(room)) {
                is UiText -> {
                    _uiStateFlow.update {
                        UiState.Error(result)
                    }
                }

                null -> {
                    fetchRooms()
                }
            }
        }
    }

    fun onNewSearchQuery(query: String) {
        _searchQuery.value = query
        onSearch()
    }

    fun onSearch() {
        when (_uiStateFlow.value) {
            is UiState.Success -> {
                _uiStateFlow.update { state ->
                    (state as UiState.Success).copy(
                        data = roomsCopy.filter { room ->
                            room.roomName.contains(_searchQuery.value, true) ||
                                    room.roomFeatures.any { it.contains(_searchQuery.value, true) }
                                    || room.numberOfPeople.toString().contains(_searchQuery.value, true)
                        }
                    )
                }
            }

            else -> {}
        }
    }

    fun resetList() {
        when (_uiStateFlow.value) {
            is UiState.Success -> {
                _uiStateFlow.update { state ->
                    (state as UiState.Success).copy(
                        data = roomsCopy
                    )
                }
            }

            else -> {}
        }
    }
}
