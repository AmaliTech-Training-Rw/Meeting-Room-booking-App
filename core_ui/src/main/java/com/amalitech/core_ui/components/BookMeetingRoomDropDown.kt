package com.amalitech.core_ui.components

import android.view.KeyEvent
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.amalitech.core.R
import com.amalitech.core_ui.theme.LocalSpacing


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun BookMeetingRoomDropDown(
    isDropDownExpanded: Boolean,
    items: List<String>,
    onSelectedItemChange: (item: String) -> Unit,
    onIsExpandedStateChange: (Boolean) -> Unit,
    selectedItem: String,
    focusManager: FocusManager,
    label: Int,
    onDopDownStatusChange: (Boolean) -> Unit
) {
    val spacing = LocalSpacing.current
    Box(Modifier.wrapContentSize(Alignment.TopStart)) {
        TextField(
            value = selectedItem,
            onValueChange = {
                onSelectedItemChange(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.2f),
                    shape = RoundedCornerShape(spacing.spaceExtraSmall)
                )
                .padding(spacing.spaceExtraSmall)
                .onPreviewKeyEvent {
                    if (it.key == Key.Tab && it.nativeKeyEvent.action == KeyEvent.ACTION_DOWN) {
                        focusManager.moveFocus(FocusDirection.Down)
                        true
                    } else {
                        false
                    }
                },
            placeholder = {
                Text(stringResource(label))
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            ),
            enabled = false,
            trailingIcon = {
                val image = if (isDropDownExpanded)
                    com.amalitech.core_ui.R.drawable.baseline_arrow_drop_up_24
                else
                    com.amalitech.core_ui.R.drawable.baseline_arrow_drop_down_24
                val description = if (isDropDownExpanded)
                    stringResource(id = R.string.close_organization_type)
                else
                    stringResource(id = R.string.open_organization_type)

                IconButton(onClick = { onDopDownStatusChange(!isDropDownExpanded) }) {
                    Icon(
                        painter = painterResource(id = image),
                        contentDescription = description
                    )
                }
            },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,
                focusedContainerColor = MaterialTheme.colorScheme.background,
                disabledTextColor = MaterialTheme.colorScheme.onBackground,
                disabledContainerColor = MaterialTheme.colorScheme.background,
                disabledIndicatorColor = Color.Transparent
            ),
        )
        DropdownMenu(
            expanded = isDropDownExpanded,
            onDismissRequest = { onDopDownStatusChange(false) },
            modifier = Modifier.padding(spacing.spaceMedium)
        ) {
            items.forEach { type ->
                DropdownMenuItem(
                    text = {
                        Text(type)
                    },
                    onClick = {
                        onSelectedItemChange(type)
                        onIsExpandedStateChange(false)
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = MaterialTheme.colorScheme.onBackground,
                    )
                )
            }
        }
    }
}
