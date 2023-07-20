package com.amalitech.home.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toSnackbarMessage
import com.amalitech.home.use_case.GetRoomList
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class RoomViewModel(
    private val getRoomList: GetRoomList
): ViewModel() {
    private val _state = MutableStateFlow(RoomUiState())
    val state: StateFlow<RoomUiState> get() = _state


    init {
        _state.value = RoomUiState()
        subscribeToLocalRoom()
    }

    private fun subscribeToLocalRoom() {
        _state.value = state.value.copy( loading = true)
        launchCatching {

        }
        viewModelScope.launch {
//            getRoomList.collect { it ->
//                _state.value = state.value.copy(loading = false, rooms = it.distinctBy { it.Name })
//            }
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

//class RoomViewModel(
//    private val roomListUseCase: RoomListUseCase
//) : BaseViewModel<RoomUiState>() {
//
//    private val _uiState = mutableStateOf(HomeUiState())
//    val uiState: State<HomeUiState> get () = _uiState
//
//    init {
//        refreshRoomsList()
//    }
//
//    private fun refreshRoomsList() {
//        launchCatching {
//            _uiStateFlow.update {
//                UiState.Loading()
//            }
//
//            val response = roomListUseCase.fetchRooms
//            if (response.invoke().data != null) {
//                val rooms = toRoomsUiStateMap(response.invoke().data!!)
//                _uiStateFlow.update {
//                    UiState.Success(
//                        RoomUiState(
//                            rooms = rooms
//                        )
//                    )
//                }
//            } else if (response.invoke().error != null) {
//                SnackbarManager
//                    .showMessage(SnackbarMessage.StringSnackbar((response.invoke().error.toString())))
//            } else {
//                _uiStateFlow.update {
//                    UiState.Error(
//                        UiText.StringResource(R.string.generic_error)
//                    )
//                }
//                SnackbarManager.showMessage(R.string.generic_error)
//            }
//        }
//    }
//
//    internal fun toRoomsUiStateMap(data: List<Room>) =
//        data
//            .map { room: Room ->
//                RoomItemUiState(
//                    room.room_name,
//                    room.number_of_people,
//                    room.description,
//                    room.imageRes
//                )
//            }
//}