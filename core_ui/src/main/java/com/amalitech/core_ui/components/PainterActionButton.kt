package com.amalitech.core_ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.amalitech.core_ui.R

@Composable
fun PainterActionButton(
    modifier: Modifier = Modifier,
    contentDescription: String = stringResource(id = R.string.open_profile_screen),
    painter: Painter = painterResource(id = R.drawable.drawer_user),
    onClick: () -> Unit,
) {
    IconButton(onClick = onClick) {
        Image(
            painter = painter,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
