package com.amalitech.rooms

import androidx.lifecycle.viewModelScope
import com.amalitech.core.data.model.Room
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.util.BaseViewModel
import com.amalitech.rooms.usecase.RoomUseCaseWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RoomViewModel(
    private val useCaseWrapper: RoomUseCaseWrapper
) : BaseViewModel<List<Room>>() {
    private val _uiState = MutableStateFlow(RoomListUiState())
    val uiState: StateFlow<RoomListUiState> = _uiState.asStateFlow()

    init {
        fetchRooms()
    }

    internal fun fetchRooms() {
        if (job?.isActive == true)
            return
        viewModelScope.launch {
            _uiState.update {
                it.copy(loading = true)
            }
            val response = useCaseWrapper.fetchRoomsUseCase()
            if (response.data != null) {
                response.data?.let { list ->
                    _uiState.update {
                        it.copy(
                            rooms = list,
                            roomsCopy = list,
                            loading = false
                        )
                    }
                }
            } else if (response.error != null) {
                _uiState.update {
                    it.copy(
                        error = response.error,
                        loading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        error = UiText.StringResource(com.amalitech.core.R.string.error_default_message),
                        loading = false
                    )
                }
            }
        }
    }

    fun deleteRoom(room: Room) {
        viewModelScope.launch {
            val result = useCaseWrapper.deleteRoomUseCase(room)
            result?.let { error ->
                _uiState.update {
                    it.copy(
                        error = error,
                        loading = false
                    )
                }
            }
            if (result == null)
                fetchRooms()
        }
    }

    fun onNewSearchQuery(query: String) {
        _uiState.update {
            it.copy(
                searchQuery = query
            )
        }
        onSearch()
    }

    fun onSearch() {
        _uiState.update { roomListUiState ->
            roomListUiState.copy(
                rooms = roomListUiState.roomsCopy.filter { room ->
                    room.roomName.contains(roomListUiState.searchQuery, true) ||
                            room.roomFeatures.any { it.contains(roomListUiState.searchQuery, true) }
                            || room.numberOfPeople.toString()
                        .contains(roomListUiState.searchQuery, true)
                }
            )
        }
    }

    fun resetList() {
        _uiState.update {
            it.copy(
                rooms = it.roomsCopy
            )
        }
    }

    fun clearError() {
        _uiState.update {
            it.copy(error = null)
        }
    }
}
