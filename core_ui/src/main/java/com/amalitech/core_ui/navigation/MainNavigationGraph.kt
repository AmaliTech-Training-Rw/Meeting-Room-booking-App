package com.amalitech.core_ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.navigation

const val mainNavigationRoute = "main_route"

fun NavGraphBuilder.MainNavigationGraph(
    startDestination: String,
    appState: BookM,
    innerPadding: PaddingValues,
) {
    navigation(
        route = mainNavigationRoute,
        startDestination = startDestination
    ) {
        HomeScreen(
            startDestination = infoNavigationRoute
        )
    }
}