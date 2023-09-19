package com.amalitech.bookmeetingroom.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import coil.compose.AsyncImage
import com.amalitech.core.util.UserInfo
import com.amalitech.core_ui.CoreViewModel
import com.amalitech.core_ui.R
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun BookMeetingRoomDrawer(
    appState: BookMeetingRoomAppState,
    onClick: (screen: NavigationItem) -> Unit,
    content: @Composable () -> Unit
) {
    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContentColor = MaterialTheme.colorScheme.onBackground,
                content = {
                    DrawerHeader()
                    LazyColumn {
                        items(NavigationItem.createItems()) { item ->
                            DrawerNavigationItem(
                                appState,
                                item,
                                onClick
                            )
                        }
                    }
                },
                drawerContainerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.width(300.dp)
            )
        },
        content = {
            content()
        },
        scrimColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    )
}

@Composable
fun DrawerNavigationItem(
    appState: BookMeetingRoomAppState,
    item: NavigationItem,
    onClick: (screen: NavigationItem) -> Unit
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    val navBackStackEntry by appState.navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination

    NavigationDrawerItem(
        icon = {
            Icon(
                item.icon,
                contentDescription = null
            )
        },
        label = { Text(item.title) },
        selected = currentRoute?.hierarchy?.any {
            it.route == item.route
        } == true,
        onClick = {
            coroutineScope.launch { appState.drawerState.close() }
            onClick(item)
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Transparent,
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.onBackground,
            unselectedIconColor = MaterialTheme.colorScheme.onBackground
        )
    )
}

@Composable
fun DrawerHeader(
    viewModel: CoreViewModel = koinViewModel()
) {
    val userInfo by viewModel.userInfo.collectAsState(UserInfo())
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = userInfo.url,
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.height(12.dp))
        Text(userInfo.name, color = MaterialTheme.colorScheme.onBackground)
        Spacer(Modifier.height(12.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(MaterialTheme.colorScheme.onBackground)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DrawerHeaderPreview() {
    BookMeetingRoomTheme {
        DrawerHeader()
    }
}

@Preview(showBackground = true)
@Composable
fun BookMeetingRoomDrawerPreview() {
    val appState = rememberBookMeetingRoomAppState()
    BookMeetingRoomTheme {
        BookMeetingRoomDrawer(
            appState,
            {}
        ) {}
    }
}