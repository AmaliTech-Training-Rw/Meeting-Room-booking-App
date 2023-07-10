package com.example.room.book_room.use_case

import com.amalitech.core.domain.use_case.ValidateEmail

data class BookRoomUseCase(
    val getBookableRoomUseCase: GetBookableRoomUseCase,
    val validateEmail: ValidateEmail,
    val bookRoom: BookRoom
)
