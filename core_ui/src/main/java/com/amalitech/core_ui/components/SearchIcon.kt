package com.amalitech.core_ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.amalitech.core_ui.R


@Composable
fun SearchIcon(
    searchQuery: String?,
    onSearch: (() -> Unit)?,
    onSearchQueryChange: ((query: String) -> Unit)?,
    isSearchTextFieldVisible: Boolean,
    onSearchTextFieldVisibilityChanged: ((isSearching: Boolean) -> Unit)?,
    searchIcon: Painter = painterResource(id = R.drawable.baseline_search_24),
    closeIcon: Painter = painterResource(id = R.drawable.baseline_close_24),
    focusedIndicatorColor: Color = Color.Transparent,
    unfocusedIndicatorColor: Color = Color.Transparent,
    focusedTextColor: Color = MaterialTheme.colorScheme.onBackground,
    unfocusedContainerColor: Color = MaterialTheme.colorScheme.background,
    unfocusedTextColor: Color = MaterialTheme.colorScheme.onBackground,
    focusedContainerColor: Color = MaterialTheme.colorScheme.background,
    placeholder: String = stringResource(id = R.string.search)
) {
    if (
        searchQuery != null
        && onSearch != null
        && onSearchQueryChange != null
        && onSearchTextFieldVisibilityChanged != null
    ) {
        if (!isSearchTextFieldVisible) {
            IconButton(onClick = { onSearchTextFieldVisibilityChanged(true) }) {
                Icon(
                    painter = searchIcon,
                    contentDescription = stringResource(R.string.open_search)
                )
            }
        } else {
            TextField(
                value = searchQuery,
                onValueChange = { query ->
                    onSearchQueryChange(query)
                },
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = {
                    IconButton(onClick = onSearch) {
                        Icon(
                            painter = searchIcon,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { onSearchTextFieldVisibilityChanged(false) }) {
                        Icon(
                            painter = closeIcon,
                            contentDescription = stringResource(R.string.close_search_textfield)
                        )
                    }
                },
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = focusedIndicatorColor,
                    unfocusedIndicatorColor = unfocusedIndicatorColor,
                    focusedTextColor = focusedTextColor,
                    unfocusedContainerColor = unfocusedContainerColor,
                    unfocusedTextColor = unfocusedTextColor,
                    focusedContainerColor = focusedContainerColor,
                ),
                placeholder = {
                    Text(placeholder)
                }
            )
        }
    }
}
