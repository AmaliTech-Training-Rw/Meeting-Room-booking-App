package com.amalitech.core_ui.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.amalitech.core_ui.bottom_navigation.components.BottomNavBadge
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.bottom_navigation.components.NavItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    selectedIconColor: Color = MaterialTheme.colorScheme.primary,
    selectedTextColor: Color = MaterialTheme.colorScheme.primary,
    indicatorColor: Color = MaterialTheme.colorScheme.background,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    badgeBackgroundColor: Color = MaterialTheme.colorScheme.error,
    badgeTextColor: Color = MaterialTheme.colorScheme.onError
) {
    var selectedIndex by remember {
        mutableStateOf(NavItem.Home.index)
    }
    val context = LocalContext.current
    val invitations by remember {
        mutableStateOf(5)
    }
    NavigationBar(
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = Modifier
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = contentColor.copy(alpha = 0.3f)
            )
    ) {
        BottomNavItem.items.forEach { item ->
            var currentItem = item
            if (currentItem.index == NavItem.Invitations.index)
                currentItem = currentItem.copy(
                    badge = BottomNavBadge.CountBadge(invitations)
                )
            NavigationBarItem(
                selected = selectedIndex == currentItem.index,
                onClick = {
                    selectedIndex = currentItem.index
                },
                icon = {
                    Icon(
                        painter = painterResource(id = currentItem.icon),
                        contentDescription = currentItem.label.asString(context),
                    )
                },
                label = {
                    BadgedBox(badge = {
                        if (currentItem.badge.count > 0)
                            Text(
                                currentItem.badge.count.toString(),
                                color = badgeTextColor,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(badgeBackgroundColor)
                            )
                    }) {
                        Text(
                            text = currentItem.label.asString(context),
                            maxLines = 1,
                            fontSize = 10.sp
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    selectedTextColor = selectedTextColor,
                    indicatorColor = indicatorColor
                )
            )
        }
    }
}

@Preview
@Composable
fun Prev() {
    BottomNavBar()
}
