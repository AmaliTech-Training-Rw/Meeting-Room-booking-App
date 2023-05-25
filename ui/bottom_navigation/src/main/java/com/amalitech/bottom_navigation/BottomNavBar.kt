package com.amalitech.bottom_navigation

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.bottom_navigation.components.BottomNavItem
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomNavBar(
    viewModel: BottomNavigationViewModel = koinViewModel()
) {
    var selectedIndex by remember {
        mutableStateOf(0)
    }
    val context = LocalContext.current
    val invitations = viewModel.invitations.collectAsStateWithLifecycle()

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onBackground,
        modifier = Modifier
            .padding(1.dp)
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.3f)
            )
    ) {
        BottomNavItem.items.forEach { item ->
            var currentItem = item
            if (currentItem.index == BottomNavItem.INVITATION_INDEX)
                currentItem = currentItem.copy(
                    badge = invitations.value
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
                        if (currentItem.badge != null)
                            Text(
                                currentItem.badge.toString(),
                                color = MaterialTheme.colorScheme.onError,
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.error)
                            )
                    }) {
                        Text(
                            text = currentItem.label.asString(context),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.background
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
