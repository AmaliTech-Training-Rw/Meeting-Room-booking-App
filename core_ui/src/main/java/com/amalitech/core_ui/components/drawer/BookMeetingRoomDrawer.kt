package com.amalitech.core_ui.components.drawer

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.navigation.NavDestination.Companion.hierarchy
import com.amalitech.core_ui.R
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.NavigationItem
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import com.amalitech.core_ui.ui.BookMeetingRoomApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BookMeetingRoomDrawer(
    appState: BookMeetingRoomAppState,
    onClick: (screen: NavigationItem) -> Unit,
    searchQuery: String? = null,
    onSearchQueryChange: ((query: String) -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    isSearchTextFieldVisible: Boolean = false,
    onSearchTextFieldVisibilityChange: ((Boolean) -> Unit)? = null
) {
    val selectedItem = remember { mutableStateOf(NavigationItem.Home.title) }

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = MaterialTheme.colorScheme.onPrimary
            ) {
                DrawerHeader()
                NavigationItem.createItems().forEach { item ->
                    DrawerNavigationItem(
                        appState,
                        item,
                        onClick,
                        selectedItem
                    )
                }
            }
        },
        content = {
            BookMeetingRoomApp(
                appState = appState,
                title = selectedItem.value,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onSearchClick = onSearchClick,
                isSearchTextFieldVisible = isSearchTextFieldVisible,
                onSearchTextFieldVisibilityChange = onSearchTextFieldVisibilityChange
            )
        }
    )
}

@Composable
fun DrawerNavigationItem(
    appState: BookMeetingRoomAppState,
    item: NavigationItem,
    onClick: (screen: NavigationItem) -> Unit,
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
        selected = appState.currentDestination?.hierarchy?.any {
            it.route == item.route
        } == true,
        onClick = {
            coroutineScope.launch { appState.drawerState.close() }
            selectedItem.value = item.title
            onClick(item)
        },
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = Color.Transparent,
            unselectedContainerColor = Color.Transparent,
            selectedTextColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.scrim,
            unselectedIconColor = MaterialTheme.colorScheme.scrim
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
                .background(MaterialTheme.colorScheme.scrim)
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
            {})
    }
}
