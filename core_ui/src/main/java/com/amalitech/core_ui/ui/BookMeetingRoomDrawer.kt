package com.amalitech.core_ui.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.R
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.example.core_ui.ui.theme.BookMeetingRoomTheme
import kotlinx.coroutines.launch


@Composable
fun BookMeetingRoomDrawer(appState: BookMeetingRoomAppState) {
    val drawerItems = mapOf(
        Pair("Home", Icons.Default.Home),
        Pair("Booking Requests", Icons.Default.Home),
        Pair("Users", Icons.Default.Home),
        Pair("Rooms", Icons.Default.Home),
        Pair("Booking History", Icons.Default.Home),
        Pair("Profile", Icons.Default.Home),
        Pair("Dashboard", Icons.Default.Home),
        Pair("Logout", Icons.Default.Home)
    )

    val selectedItem = remember { mutableStateOf(drawerItems.keys.first()) }

    ModalNavigationDrawer(
        drawerState = appState.drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(8.dp))
                DrawerHeader()
                Spacer(Modifier.height(8.dp))
                drawerItems.forEach { item ->
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

                                }

                                "Rooms" -> {

                                }

                                "Booking History" -> {

                                }

                                "Profile" -> {
                                    appState.navigate(profileNavigationRoute)
                                }

                                "Dashboard" -> {

                                }

                                "Logout" -> {

                                }
                            }
                        },
                        //modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        colors = NavigationDrawerItemDefaults.colors(
//                            selectedContainerColor =,
//                            unselectedContainerColor =,
//                            selectedIconColor =,
//                            unselectedIconColor =,
//                            selectedTextColor =,
//                            unselectedTextColor =,
//                            selectedBadgeColor =,
//                            unselectedBadgeColor =,
                        )
                    )
                }
                Spacer(Modifier.height(12.dp))
                // DrawerFooter()
            }
        },
        content = {
            BookMeetingRoomApp(appState)
        },
        scrimColor = Color.Transparent,
    )
}

@Composable
fun DrawerHeader() {
    Row(
        modifier = Modifier.padding(15.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        val image: Painter = painterResource(id = R.drawable.user)
        Image(
            painter = image,
            contentDescription = "",
            modifier = Modifier.size(100.dp)
        )
        Text(
            buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W900,
                    )
                ) {
                    append("My - ")
                }
                withStyle(
                    style = SpanStyle(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.W900,
                        color = Color(0xFF4552B8)
                    )
                ) {
                    append("Fitness")
                }
            }
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