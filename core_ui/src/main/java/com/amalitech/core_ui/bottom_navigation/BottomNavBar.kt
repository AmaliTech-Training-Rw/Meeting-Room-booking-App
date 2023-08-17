package com.amalitech.core_ui.bottom_navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import com.amalitech.core_ui.R
import com.amalitech.core_ui.bottom_navigation.components.BottomNavBadge
import com.amalitech.core_ui.bottom_navigation.components.BottomNavItem
import com.amalitech.core_ui.theme.LocalSpacing

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    selectedIconColor: Color = MaterialTheme.colorScheme.primary,
    selectedTextColor: Color = MaterialTheme.colorScheme.primary,
    indicatorColor: Color = MaterialTheme.colorScheme.background,
    containerColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.onBackground,
    badgeBackgroundColor: Color = MaterialTheme.colorScheme.error,
    badgeTextColor: Color = MaterialTheme.colorScheme.onError,
    unselectedTextColor: Color = MaterialTheme.colorScheme.onBackground,
    unselectedIconColor: Color = MaterialTheme.colorScheme.onBackground,
    currentDestination: NavDestination?,
    onClick: (screen: BottomNavItem) -> Unit
) {
    val context = LocalContext.current
    val invitations by remember {
        mutableIntStateOf(200)
    }
    val spacing = LocalSpacing.current

    NavigationBar(
        containerColor = containerColor,
        contentColor = contentColor,
        modifier = Modifier
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = contentColor.copy(alpha = 0.3f)
            )
            .padding(spacing.spaceExtraSmall)
    ) {
        BottomNavItem.createItems().forEach { screen ->
            if (screen.route == BottomNavItem.Invitations.route)
                screen.badge = BottomNavBadge.CountBadge(invitations)

            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.route == screen.route
                } == true,
                onClick = { onClick(screen) },
                icon = {
                    BadgedBox(badge = {
                        if (screen.badge.count > 0) {
                            val count = if (screen.badge.count < 100)
                                screen.badge.count.toString()
                            else stringResource(id = R.string.ninety_plus)
                            Text(
                                text = count,
                                color = badgeTextColor,
                                modifier = Modifier
                                    .height(spacing.spaceSmall + spacing.spaceMedium)
                                    .clip(CircleShape)
                                    .background(badgeBackgroundColor)
                                    .padding(1.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }) {
                        Icon(
                            painter = painterResource(id = screen.icon),
                            contentDescription = screen.label.asString(context),
                        )
                    }
                },
                label = {
                    Text(
                        text = screen.label.asString(context),
                        maxLines = 1,
                        fontSize = 10.sp
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = selectedIconColor,
                    selectedTextColor = selectedTextColor,
                    indicatorColor = indicatorColor,
                    unselectedTextColor = unselectedTextColor,
                    unselectedIconColor = unselectedIconColor,
                )
            )
        }
    }
}
