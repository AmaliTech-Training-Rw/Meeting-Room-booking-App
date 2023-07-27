package com.amalitech.rooms.book_room.use_case

import com.amalitech.core.domain.use_case.ValidateEmailUseCase

data class BookRoomUseCasesWrapper(
    val getRoomUseCase: GetRoomUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val bookRoomUseCase: BookRoomUseCase
)
