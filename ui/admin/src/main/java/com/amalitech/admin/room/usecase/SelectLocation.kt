package com.amalitech.admin.room.usecase

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class GetLocation {
    operator fun invoke(): Flow<List<String>> =
        flowOf( // TODO: GET LIST FROM A SOURCE APPROPRIATELY
            listOf(
                "Rwanda",
                "Kampala",
                "BURUNDI"
            )
        )
}



