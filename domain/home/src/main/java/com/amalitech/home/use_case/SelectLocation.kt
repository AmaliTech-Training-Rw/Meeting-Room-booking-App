package com.amalitech.home.use_case

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



