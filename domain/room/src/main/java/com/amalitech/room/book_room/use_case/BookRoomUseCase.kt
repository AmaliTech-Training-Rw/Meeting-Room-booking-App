package com.amalitech.room.book_room.use_case

import com.amalitech.core.domain.use_case.ValidateEmail

data class BookRoomUseCase(
    val getBookableRoomUseCase: com.amalitech.room.book_room.use_case.GetBookableRoomUseCase,
    val validateEmail: ValidateEmail,
    val bookRoom: com.amalitech.room.book_room.use_case.BookRoom
)
