package com.amalitech.rooms

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

    internal fun fetchRooms() {
        if (job?.isActive == true)
            return
        viewModelScope.launch {
            _uiStateFlow.update {
                UiState.Loading()
            }
            val response = useCaseWrapper.fetchRoomsUseCase()
            if (response.data != null) {
                _uiStateFlow.update {
                    UiState.Success(response.data)
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
            when(val result = useCaseWrapper.deleteRoomUseCase(room)) {
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
}
