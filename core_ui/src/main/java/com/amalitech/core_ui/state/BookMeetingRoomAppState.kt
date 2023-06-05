package com.amalitech.core_ui.state

import android.content.res.Resources
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amalitech.core_ui.util.SnackbarManager
import com.amalitech.core_ui.util.SnackbarMessage.Companion.toMessage
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun rememberBookMeetingRoomAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    ),
    systemUiController: SystemUiController = rememberSystemUiController(),
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    snackbarManager: SnackbarManager = SnackbarManager,
    resources: Resources = resources(),
): BookMeetingRoomAppState {
    return remember(
        navController,
        coroutineScope,
        drawerState,
        systemUiController,
        snackbarHostState,
        snackbarManager,
        resources
    ) {
        BookMeetingRoomAppState(
            navController,
            coroutineScope,
            drawerState,
            systemUiController,
            snackbarHostState,
            snackbarManager,
            resources
        )
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Stable
class BookMeetingRoomAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
    val drawerState: DrawerState,
    val systemUiController: SystemUiController,
    val snackbarHostState: SnackbarHostState,
    val snackbarManager: SnackbarManager,
    val resources: Resources
) {

    init {
        coroutineScope.launch {
            snackbarManager.snackbarMessages.filterNotNull().collect { snackbarMessage ->
                val text = snackbarMessage.toMessage(resources)
                snackbarHostState.showSnackbar(text)
            }
        }
    }

    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val currentTopLevelDestination: NavigationItem?
        @Composable get() = when (currentDestination?.route) {
            NavigationItem.Home.route -> NavigationItem.Home
            NavigationItem.Profile.route -> NavigationItem.Profile
            NavigationItem.BookingRequests.route -> NavigationItem.BookingRequests
            NavigationItem.Invitations.route -> NavigationItem.Invitations
            else -> null
        }


    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        navController.navigate(route) { launchSingleTop = true }
    }

    fun navigateAndPopUp(route: String, popUp: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}
