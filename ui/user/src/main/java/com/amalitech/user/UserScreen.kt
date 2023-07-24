package com.amalitech.user

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.amalitech.core_ui.theme.BookMeetingRoomTheme

@Composable
fun UserScreen() {
    Text(
        "user screen"
    )
}

@Preview(showBackground = true)
@Composable
fun UserScreenPreview() {
    BookMeetingRoomTheme {
        UserScreen()
    }
}