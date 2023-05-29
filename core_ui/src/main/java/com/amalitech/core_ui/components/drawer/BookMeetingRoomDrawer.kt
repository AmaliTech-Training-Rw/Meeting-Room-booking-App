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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.R
import com.amalitech.core_ui.navigation.homeNavigationRoute
import com.amalitech.core_ui.navigation.myBookingsNavigationRoute
import com.amalitech.core_ui.navigation.profileNavigationRoute
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.ui.BookMeetingRoomApp
import kotlinx.coroutines.launch

@Composable
fun BookMeetingRoomDrawer(appState: BookMeetingRoomAppState) {
    val items = listOf(
        DrawerItem.Home,
        DrawerItem.BookingRequests,
        DrawerItem.Users,
        DrawerItem.Rooms,
        DrawerItem.BookingHistory,
        DrawerItem.Profile,
        DrawerItem.Dashboard,
        DrawerItem.Logout
    )

    val selectedItem = remember { mutableStateOf(DrawerItem.Home.title) }

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
    item: DrawerItem,
    selectedItem: MutableState<String>
) {
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
            appState.coroutineScope.launch { appState.drawerState.close() }
            selectedItem.value = item.title
            when (item) {
                is DrawerItem.Home -> {
                    appState.navigate(homeNavigationRoute)
                }

                is DrawerItem.BookingRequests -> {
                    appState.navigate(myBookingsNavigationRoute)
                }

                is DrawerItem.Users -> {
                    appState.navigate(homeNavigationRoute)
                }

                is DrawerItem.Rooms -> {
                    appState.navigate(homeNavigationRoute)
                }

                is DrawerItem.BookingHistory -> {
                    appState.navigate(homeNavigationRoute)
                }

                is DrawerItem.Profile -> {
                    appState.navigate(profileNavigationRoute)
                }

                is DrawerItem.Dashboard -> {
                    appState.navigate(homeNavigationRoute)
                }

                is DrawerItem.Logout -> {
                    appState.navigate(homeNavigationRoute)
                }
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
