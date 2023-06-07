package com.amalitech.onboarding.components

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.amalitech.onboarding.util.Result
import com.amalitech.core.R


@Composable
fun AuthenticationDropDown(
    isDropDownExpanded: Boolean,
    items: Result<String>,
    onSelectedItemChange: (item: String) -> Unit,
    onRetry: () -> Unit,
    onIsExpandedStateChange: (Boolean) -> Unit
) {
    DropdownMenu(
        expanded = isDropDownExpanded,
        onDismissRequest = { onIsExpandedStateChange(false) },
    ) {
        when (items) {
            is Result.Success -> {
                items.data.forEach { type ->
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

            is Result.Error -> {
                IconButton(onClick = onRetry) {
                    Icon(
                        painter = painterResource(com.amalitech.ui.onboarding.R.drawable.baseline_replay_24),
                        contentDescription = stringResource(R.string.reload_organization_type)
                    )
                }
            }

            is Result.Loading -> {
                CircularProgressIndicator()
            }
        }
    }
}
