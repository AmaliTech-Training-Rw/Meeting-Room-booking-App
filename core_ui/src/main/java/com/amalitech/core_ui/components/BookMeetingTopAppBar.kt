package com.amalitech.core_ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookMeetingTopAppBar(
    appBarState: AppBarState
) {
    if (appBarState.hasTopBar)
    {
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
            modifier = Modifier.shadow(elevation = 24.dp),
            navigationIcon = {
                appBarState.navigationIcon?.invoke()
            },
            actions = {
                appBarState.actions?.invoke(this)
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BookMeetingTopAppBarPreview() {
    BookMeetingRoomTheme {
        BookMeetingTopAppBar(appBarState = AppBarState())
    }
}
