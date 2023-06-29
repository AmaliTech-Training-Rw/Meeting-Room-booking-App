package com.amalitech.home.components

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
import com.amalitech.ui.home.R

@Composable
fun HomeTab(
    onTabSelected: (tab: HomeTab) -> Unit,
    selectedTab: HomeTab,
    modifier: Modifier = Modifier,
    tabs: List<HomeTab> = HomeTab.createHomeTabsList(),
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
    tab: HomeTab,
    onTabSelected: (tab: HomeTab) -> Unit,
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

sealed class HomeTab(val name: UiText.StringResource) {
    object Rooms : HomeTab(UiText.StringResource(R.string.rooms))
    object Calendar : HomeTab(UiText.StringResource(R.string.calendar))

    companion object {
        fun createHomeTabsList() = listOf(Rooms, Calendar)
    }
}
