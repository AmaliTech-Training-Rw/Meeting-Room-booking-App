package com.amalitech.rooms

import androidx.compose.runtime.Composable

data class FloatingActionButton(
    val action: (@Composable () -> Unit)? = null
)
