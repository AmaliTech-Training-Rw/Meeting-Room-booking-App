package com.amalitech.core_ui.state

import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log
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
import com.amalitech.core_ui.domain.TopLevelDestination
import com.amalitech.core_ui.navigation.homeNavigationRoute
import com.amalitech.core_ui.navigation.invitationsNavigationRoute
import com.amalitech.core_ui.navigation.myBookingsNavigationRoute
import com.amalitech.core_ui.navigation.profileNavigationRoute
import com.amalitech.core_ui.util.snackbar.SnackbarManager
import com.amalitech.core_ui.util.snackbar.SnackbarMessage.Companion.toMessage
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Composable
fun rememberBookMeetingRoomAppState(
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    assets: AssetManager = assets(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    systemUiController: SystemUiController = rememberSystemUiController(),
    snackbarManager: SnackbarManager = SnackbarManager,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() }
): BookMeetingRoomAppState {
    return remember(
        navController,
        resources,
        assets,
        coroutineScope,
        drawerState,
        systemUiController,
        snackbarManager,
        snackbarHostState,
    ) {
        BookMeetingRoomAppState(
            navController,
            resources,
            assets,
            coroutineScope,
            drawerState,
            systemUiController,
            snackbarManager,
            snackbarHostState
        )
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}

@Composable
@ReadOnlyComposable
fun assets(): AssetManager {
    LocalConfiguration.current
    return LocalContext.current.assets
}

@Stable
class BookMeetingRoomAppState(
    val navController: NavHostController,
    val resources: Resources,
    val assets: AssetManager,
    val coroutineScope: CoroutineScope,
    val drawerState: DrawerState,
    val systemUiController: SystemUiController,
    val snackbarManager: SnackbarManager,
    val snackbarHostState: SnackbarHostState,
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

    val currentTopLevelDestination: TopLevelDestination?
        @Composable get() = when (currentDestination?.route) {
            homeNavigationRoute -> TopLevelDestination.HOME
            profileNavigationRoute -> TopLevelDestination.PROFILE
            myBookingsNavigationRoute -> TopLevelDestination.BOOKING_REQUESTS
            invitationsNavigationRoute -> TopLevelDestination.INVITATIONS
            else -> null
        }


    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: String) {
        Log.d("TAG", "navigate: route $route")
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
