package com.amalitech.bookmeetingroom.testComponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amalitech.bookmeetingroom.navigation.NavigationTarget
import com.amalitech.core_ui.components.DefaultButton

@Composable
fun DebugScreen(onNavigate: (NavigationTarget) -> Unit) {
    val actionButtons =
        listOf(
            NavigationTarget.ONBOARD,
            NavigationTarget.SPLASH,
            NavigationTarget.LOGIN,
            NavigationTarget.FORGOT,
            NavigationTarget.RESET,
            NavigationTarget.CARD,
            NavigationTarget.DASHBOARD,
            NavigationTarget.DRAWER
        )

    LazyColumn(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        items(actionButtons) { navigationTarget ->
            Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center) {
                DefaultButton(
                    modifier = Modifier.align(CenterVertically),
                    text = navigationTarget.route,
                    onClick = { onNavigate.invoke(navigationTarget) })

            }
            Spacer(modifier = Modifier.size(16.dp))
        }
    }
}