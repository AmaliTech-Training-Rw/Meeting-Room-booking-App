package com.amalitech.core_ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import com.amalitech.core_ui.R

@Composable
fun NavigationButton(
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(id = R.string.open_drawer),
    imageVector: ImageVector = Icons.Filled.Menu,
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
