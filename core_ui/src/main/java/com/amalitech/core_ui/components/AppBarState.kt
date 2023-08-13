package com.amalitech.core_ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.runtime.Composable

data class AppBarState(
    val title: String = "",
    val actions: (@Composable RowScope.() -> Unit)? = null,
    val navigationIcon: (@Composable () -> Unit)? = null,
    val floatingActionButton: (@Composable () -> Unit)? = null,
    val hasTopBar: Boolean = true
)
