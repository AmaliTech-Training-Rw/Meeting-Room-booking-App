package com.amalitech.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.R
import com.amalitech.core_ui.state.BookMeetingRoomAppState
import com.amalitech.core_ui.state.rememberBookMeetingRoomAppState
import com.amalitech.core_ui.theme.BookMeetingRoomTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMeetingTopAppBar(
    appState: BookMeetingRoomAppState,
    title: String,
    searchQuery: String? = null,
    onSearchQueryChange: ((query: String) -> Unit)? = null,
    onSearchClick: (() -> Unit)? = null,
    isSearchTextFieldVisible: Boolean = false,
    onSearchTextFieldVisibilityChange: ((Boolean) -> Unit)? = null
) {
    val coroutineScope: CoroutineScope = rememberCoroutineScope()

    TopAppBar(
        title = {
            Text(
                title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        modifier = Modifier.shadow(elevation = 24.dp),
        navigationIcon = {
            IconButton(onClick = {
                if (appState.drawerState.isClosed) {
                    coroutineScope.launch {
                        appState.drawerState.open()
                    }
                } else {
                    coroutineScope.launch {
                        appState.drawerState.close()
                    }
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Localized description"
                )
            }
        },
        actions = {
            SearchIcon(
                searchQuery = searchQuery,
                onSearch = onSearchClick,
                onSearchQueryChange = onSearchQueryChange,
                isSearchTextFieldVisible = isSearchTextFieldVisible,
                onSearchTextFieldVisibilityChanged = onSearchTextFieldVisibilityChange,
            )
            IconButton(onClick = { /* doSomething() */ }) {
                Image(
                    painter = painterResource(id = R.drawable.drawer_user),
                    contentDescription = "user account",
                    modifier = Modifier.size(32.dp)
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BookMeetingTopAppBarPreview() {
    BookMeetingRoomTheme {
        val appState = rememberBookMeetingRoomAppState()
        BookMeetingTopAppBar(appState, "Home", "search", {}, {

        })
    }
}
