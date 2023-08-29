package com.amalitech.bookmeetingroom

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.amalitech.bookmeetingroom.navigation.AppNavHost
import com.amalitech.core_ui.bottom_navigation.BottomNavBar
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.components.AppBarState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(shouldShowOnboarding: Boolean, onFinishActivity: () -> Unit) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination
    val snackbarHostState = remember {
        SnackbarHostState()
    }
    var appBarState by remember {
        mutableStateOf(AppBarState(hasTopBar = false))
    }

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
        },
        snackbarHost = {
            SnackbarHost(snackbarHostState)
        },
        topBar = {
            if (appBarState.hasTopBar) {
                TopAppBar(
                    title = {
                        Text(
                            appBarState.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    },
                    navigationIcon = {
                        appBarState.navigationIcon?.invoke()
                    },
                    actions = {
                        appBarState.actions?.invoke(this)
                    },
                    modifier = Modifier.shadow(elevation = 2.dp),
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.background,
                        navigationIconContentColor = MaterialTheme.colorScheme.onBackground,
                        titleContentColor = MaterialTheme.colorScheme.onBackground,
                        actionIconContentColor = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }
        }
    ) { paddingValues ->
        AppNavHost(
            navController = navController,
            shouldShowOnboarding = shouldShowOnboarding,
            modifier = Modifier.padding(paddingValues),
            snackbarHostState = snackbarHostState,
            onComposing = {
                appBarState = it
            },
            onFinishActivity = onFinishActivity
        )
    }
}
