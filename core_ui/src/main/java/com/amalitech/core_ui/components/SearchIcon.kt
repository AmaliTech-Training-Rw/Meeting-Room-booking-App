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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.amalitech.core_ui.R


@Composable
fun SearchIcon(
    searchQuery: String?,
    onSearch: (() -> Unit)?,
    onSearchQueryChange: ((query: String) -> Unit)?,
    isSearchTextFieldVisible: Boolean,
    onIsSearchingChange: (isSearching: Boolean) -> Unit,
) {
    if (
        searchQuery != null
        && onSearch != null
        && onSearchQueryChange != null
    ) {
        if (!isSearchTextFieldVisible) {
            IconButton(onClick = { onIsSearchingChange(true) }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_search_24),
                    contentDescription = stringResource(
                        R.string.open_search
                    )
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
                            painter = painterResource(R.drawable.baseline_search_24),
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = { onIsSearchingChange(false) }) {
                        Icon(
                            painter = painterResource(R.drawable.baseline_close_24),
                            contentDescription = stringResource(R.string.close_search_textfield)
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
                ),
                placeholder = {
                    Text(stringResource(R.string.search))
                }
            )
        }
    }
}
