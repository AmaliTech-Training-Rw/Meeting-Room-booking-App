package com.amalitech.rooms

import com.amalitech.core.data.model.Room
import com.amalitech.core.util.ApiResult
import com.amalitech.core.util.UiText
import com.amalitech.rooms.usecase.RoomUseCaseWrapper
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RoomViewModelTest {

    private lateinit var viewModel: RoomViewModel

    @MockK
    private lateinit var useCaseWrapper: RoomUseCaseWrapper

    @get:Rule
    var mainDispatcherRule = MainDispatcherRule()

    private val rooms = mutableListOf(
        Room(
            id = "id1",
            roomName = "Room 1",
            numberOfPeople = 5,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Whiteboard",
                "Natural light",
                "Drinks"
            ),
            imageUrl = listOf("https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png")
        ),
        Room(
            id = "id2",
            roomName = "Room 2",
            numberOfPeople = 8,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Projector",
                "Whiteboard",
                "Snacks"
            ),
            imageUrl = listOf("https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png")
        ),
        Room(
            id = "id3",
            roomName = "Room 3",
            numberOfPeople = 6,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Whiteboard",
                "Natural light",
                "Drinks"
            ),
            imageUrl = listOf("https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png")
        ),
        Room(
            id = "id4",
            roomName = "Room 4",
            numberOfPeople = 12,
            roomFeatures = listOf(
                "Air conditioning",
                "Internet",
                "Projector",
                "Whiteboard",
                "Snacks"
            ),
            imageUrl = listOf("https://res.cloudinary.com/dhw5h8j3v/image/upload/v1685995350/room_image_xfe71c.png")
        )
    )

    @Before
    fun setUp() {
        useCaseWrapper = mockk()
        viewModel = RoomViewModel(useCaseWrapper)
    }

    @Test
    fun `ensures fetch rooms update when there is no error`() {
        coEvery {
            useCaseWrapper.fetchRoomsUseCase()
        } returns ApiResult(rooms)

        viewModel.fetchRooms()

        assertEquals(false, viewModel.uiState.value.loading)
        assertEquals(rooms, viewModel.uiState.value.rooms)
    }

    @Test
    fun `ensures error is updated when fetchRooms throws`() {
        val error = UiText.DynamicString("there is an error")
        coEvery {
            useCaseWrapper.fetchRoomsUseCase()
        } returns ApiResult(error = error)

        viewModel.fetchRooms()

        assertEquals(false, viewModel.uiState.value.loading)
        assertEquals(error, viewModel.uiState.value.error)
    }

    @Test
    fun `ensures error is updated when deleteRoom throws`() {
        val error = UiText.DynamicString("there is an error")
        coEvery {
            useCaseWrapper.deleteRoomUseCase(any())
        } returns error

        viewModel.deleteRoom(rooms.first())

        assertEquals(true, viewModel.uiState.value.error != null)
        assertEquals(error, viewModel.uiState.value.error)
    }


    @Test
    fun `ensures deleteRoom update success when there is no error`() {
        coEvery {
            useCaseWrapper.fetchRoomsUseCase()
        } returns ApiResult(rooms)
        coEvery {
            useCaseWrapper.deleteRoomUseCase(any())
        } returns null

        viewModel.deleteRoom(rooms.first())

        assertEquals(false, viewModel.uiState.value.loading)
        assertEquals(rooms, viewModel.uiState.value.rooms)
    }
}