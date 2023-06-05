package com.amalitech.rooms

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.amalitech.core.data.model.Room
import com.amalitech.rooms.usecase.GetRoomsUseCase

class RoomViewModel(private val getRooms: GetRoomsUseCase) : ViewModel() {
    private val _rooms = mutableStateOf<List<Room>>(emptyList())
    val rooms: State<List<Room>> = _rooms
init {
    fetchRooms()
}
    private fun fetchRooms() {
        val fetchedRooms = getRooms.execute()
        _rooms.value = fetchedRooms
    }
}
