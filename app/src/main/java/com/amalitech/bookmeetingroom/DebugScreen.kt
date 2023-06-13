package com.amalitech.bookmeetingroom

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.components.DefaultButton

@Composable
fun DebugScreen(onNavigate: (NavigationTarget) -> Unit){
    val buttons = listOf(NavigationTarget.ONBOARDING,NavigationTarget.SPLASH,NavigationTarget.LOGIN)

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(buttons) { navigationTarget ->
                Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.Center){
                    DefaultButton(
                        modifier = Modifier.align(CenterVertically),
                        text = navigationTarget.route,
                        onClick = { onNavigate.invoke(navigationTarget) })

                }
                Spacer(modifier = Modifier.size(16.dp))
            }
    }
}