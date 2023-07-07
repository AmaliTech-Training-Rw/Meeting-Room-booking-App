package com.amalitech.home.book_room.use_case

data class BookRoomUseCase(
    val getBookableRoom: GetBookableRoom,
    val validateEmail: ValidateEmail,
    val bookRoom: BookRoom
)
