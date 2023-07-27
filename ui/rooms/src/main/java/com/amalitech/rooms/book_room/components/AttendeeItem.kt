package com.amalitech.rooms.book_room.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import com.amalitech.ui.rooms.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendeeItem(
    attendee: String,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit
) {
    InputChip(
        selected = false,
        onClick = {},
        label = {
            Text(
                text = attendee,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        },
        modifier = modifier,
        trailingIcon = {
            IconButton(onClick = { onDeleteClick() }) {
                Icon(
                    painter = painterResource(R.drawable.baseline_close_24),
                    contentDescription = stringResource(id = R.string.delete),
                )
            }
        },
        border = InputChipDefaults.inputChipBorder()
    )
}
