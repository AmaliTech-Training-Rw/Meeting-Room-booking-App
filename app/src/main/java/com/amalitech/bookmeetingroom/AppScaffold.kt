package com.amalitech.bookmeetingroom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amalitech.bookmeetingroom.navigation.AppNavHost
import com.amalitech.core_ui.bottom_navigation.BottomNavBar
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem

@Composable
fun AppScaffold(shouldShowOnboarding: Boolean) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    Scaffold(
        bottomBar = {
            if (BottomNavItem.createItems().any { it.route == currentRoute?.route }) {
                BottomNavBar(currentDestination = currentRoute, onClick = {
                    navController.navigate(it.route) {
                        popUpTo(
                            BottomNavItem.Home.route
                        ) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            shouldShowOnboarding = shouldShowOnboarding,
            modifier = Modifier.padding(paddingValues)
        )
    }

}
