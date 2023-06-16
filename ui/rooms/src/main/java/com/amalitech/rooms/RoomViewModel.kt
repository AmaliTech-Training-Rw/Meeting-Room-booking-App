package com.amalitech.rooms

import androidx.lifecycle.ViewModel
import com.amalitech.core.data.model.Room
import com.amalitech.rooms.usecase.GetRoomsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class RoomViewModel(private val getRooms: GetRoomsUseCase) : ViewModel() {
    private val _rooms = MutableStateFlow<List<Room>>(emptyList())
    val rooms: StateFlow<List<Room>> = _rooms.asStateFlow()

    init {
        fetchRooms()
    }

    private fun fetchRooms() {
        val fetchedRooms = getRooms()
        _rooms.value = fetchedRooms
    }
}
