package com.amalitech.user.profile.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

@Composable
fun ProfileDescriptionItem(
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    titleTextStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    descriptionTextStyle: TextStyle = MaterialTheme.typography.titleMedium,
    titleTextColor: Color = MaterialTheme.colorScheme.onBackground,
    descriptionTextColor: Color = MaterialTheme.colorScheme.onBackground
) {
    Column(
        modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            style = titleTextStyle,
            color = titleTextColor
        )

        Text(
            text = description,
            style = descriptionTextStyle,
            color = descriptionTextColor
        )
    }
}
