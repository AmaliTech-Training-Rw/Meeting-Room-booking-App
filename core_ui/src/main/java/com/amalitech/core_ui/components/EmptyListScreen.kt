package com.amalitech.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.amalitech.core_ui.R

@Composable
fun EmptyListScreen(
    item: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = MaterialTheme.colorScheme.background,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    iconColor: Color = MaterialTheme.colorScheme.primary,
    icon: Painter = painterResource(id = R.drawable.empty_set)
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = icon,
            contentDescription = stringResource(id = R.string.the_list_is_empty),
            tint = iconColor
        )
        Text(
            text = stringResource(R.string.the_list_is_empty),
            style = MaterialTheme.typography.titleMedium,
            color = textColor
        )
        Text(
            text = stringResource(R.string.no_item_found_retry, item),
            color = textColor
        )
    }
}
