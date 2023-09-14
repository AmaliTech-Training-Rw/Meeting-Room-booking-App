package com.amalitech.rooms.book_room.use_case

import com.amalitech.core.domain.use_case.ValidateEmailUseCase
import com.amalitech.rooms.usecase.FindRoomUseCase

data class BookRoomUseCasesWrapper(
    val getRoomUseCase: FindRoomUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val bookRoomUseCase: BookRoomUseCase
)
