package com.amalitech.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.amalitech.core.util.UiText
import com.amalitech.core_ui.R

@Composable
fun BookingAppTab(
    onTabSelected: (tab: Tab) -> Unit,
    selectedTab: Tab,
    modifier: Modifier = Modifier,
    tabs: List<Tab> = Tab.createHomeTabsList(),
    underlineColor: Color = MaterialTheme.colorScheme.primary,
    textStyle: TextStyle = MaterialTheme.typography.titleMedium,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        tabs.forEach { tab ->
            TabItem(
                tab = tab,
                onTabSelected = onTabSelected,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f),
                selected = selectedTab == tab,
                underlineColor = underlineColor,
                textStyle = textStyle
            )
        }
    }
}

@Composable
fun TabItem(
    tab: Tab,
    onTabSelected: (tab: Tab) -> Unit,
    textStyle: TextStyle,
    underlineColor: Color,
    modifier: Modifier = Modifier,
    selected: Boolean = false,
) {
    val context = LocalContext.current
    Box(
        modifier = modifier
            .clickable {
                onTabSelected(tab)
            },
    ) {
        Text(
            text = tab.name.asString(context),
            style = textStyle,
            modifier = Modifier.align(Alignment.Center)
        )
        if (selected) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .height(2.dp)
                    .fillMaxWidth()
                    .background(underlineColor)
            )
        }
    }
}

sealed class Tab(val name: UiText.StringResource) {
    object Rooms : Tab(UiText.StringResource(R.string.rooms))
    object Calendar : Tab(UiText.StringResource(R.string.calendar))

    object ActiveBookings: Tab(UiText.StringResource(R.string.active))
    object EndedBookings: Tab(UiText.StringResource(R.string.ended))

    companion object {
        fun createHomeTabsList() = listOf(Rooms, Calendar)
        fun createBookingTabsList() = listOf(ActiveBookings, EndedBookings)
    }
}
