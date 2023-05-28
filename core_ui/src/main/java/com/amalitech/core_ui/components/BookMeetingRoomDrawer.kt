package com.amalitech.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmarks
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.PersonOutline
import androidx.compose.material.icons.filled.Room
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
import androidx.compose.ui.graphics.vector.ImageVector
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
    val drawerItems = mapOf(
        Pair("Home", Icons.Default.Home),
        Pair("Booking Requests", Icons.Default.Bookmarks),
        Pair("Users", Icons.Default.People),
        Pair("Rooms", Icons.Default.Room),
        Pair("Booking History", Icons.Default.History),
        Pair("Profile", Icons.Default.PersonOutline),
        Pair("Dashboard", Icons.Default.Dashboard),
        Pair("Logout", Icons.Default.Home)
    )

    val selectedItem = remember { mutableStateOf(drawerItems.keys.first()) }

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                DrawerHeader()
                drawerItems.forEach { item ->
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
    item: Map.Entry<String, ImageVector>,
    selectedItem: MutableState<String>
) {
    NavigationDrawerItem(
        icon = {
            Icon(
                item.value,
                contentDescription = null
            )
        },
        label = { Text(item.key) },
        selected = item.key == selectedItem.value,
        onClick = {
            appState.coroutineScope.launch { appState.drawerState.close() }
            selectedItem.value = item.key
            when (item.key) {
                "Home" -> {
                    appState.navigate(homeNavigationRoute)
                }

                "Booking Requests" -> {
                    appState.navigate(myBookingsNavigationRoute)
                }

                "Users" -> {
                    appState.navigate(homeNavigationRoute)
                }

                "Rooms" -> {
                    appState.navigate(homeNavigationRoute)
                }

                "Booking History" -> {
                    appState.navigate(homeNavigationRoute)
                }

                "Profile" -> {
                    appState.navigate(profileNavigationRoute)
                }

                "Dashboard" -> {
                    appState.navigate(homeNavigationRoute)
                }

                "Logout" -> {
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
