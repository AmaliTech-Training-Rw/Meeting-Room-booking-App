package com.amalitech.core_ui.components.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.R
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.ui.BookMeetingRoomApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookMeetingRoomDrawer(appState: BookMeetingRoomAppState) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.BookingRequests,
        NavigationItem.Users,
        NavigationItem.Rooms,
        NavigationItem.BookingHistory,
        NavigationItem.Profile,
        NavigationItem.Dashboard,
        NavigationItem.Logout
    )

    val selectedItem = remember { mutableStateOf(NavigationItem.Home.title) }

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                items.forEach { item ->
                    NavigationItem(
                        appState,
                        item,
                        selectedItem
                    )
                }
            }
        },
        content = {
            BookMeetingRoomApp(appState, selectedItem.value)
        }
    )
}

@Composable
fun NavigationItem(
    appState: BookMeetingRoomAppState,
    item: NavigationItem,
    selectedItem: MutableState<String>
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()
    NavigationDrawerItem(
        icon = {
            Icon(
                item.icon,
                contentDescription = null
            )
        },
        label = { Text(item.title) },
        selected = item.title == selectedItem.value,
        onClick = {
            coroutineScope.launch { appState.drawerState.close() }
            selectedItem.value = item.title
            when (item) {
                is NavigationItem.Home -> {
                    appState.navigate(NavigationItem.Home.route)
                }

                is NavigationItem.BookingRequests -> {
                    appState.navigate(NavigationItem.BookingRequests.route)
                }

                is NavigationItem.Users -> {
                    appState.navigate(NavigationItem.Users.route)
                }

                is NavigationItem.Rooms -> {
                    appState.navigate(NavigationItem.Rooms.route)
                }

                is NavigationItem.BookingHistory -> {
                    appState.navigate(NavigationItem.BookingHistory.route)
                }

                is NavigationItem.Profile -> {
                    appState.navigate(NavigationItem.Profile.route)
                }

                is NavigationItem.Dashboard -> {
                    appState.navigate(NavigationItem.Dashboard.route)
                }

                is NavigationItem.Logout -> {
                    appState.navigate(NavigationItem.Logout.route)
                }

                else -> {}
            }
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Transparent,
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.primary,
        )
    )
}

@Composable
fun DrawerHeader() {
    Column(
        modifier = Modifier.padding(8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val image: Painter = painterResource(id = R.drawable.drawer_user)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier
                .size(112.dp)
                .clip(CircleShape)
        )
        Spacer(Modifier.height(12.dp))
        Text("Firstname   Lastname")
        Spacer(Modifier.height(12.dp))
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
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
        BookMeetingRoomDrawer(appState)
    }
}
