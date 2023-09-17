package com.amalitech.core_ui.components

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import com.amalitech.core.util.UserInfo
import com.amalitech.core_ui.CoreViewModel
import com.amalitech.core_ui.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun PainterActionButton(
    modifier: Modifier = Modifier,
    viewModel: CoreViewModel = koinViewModel(),
    contentDescription: String = stringResource(id = R.string.open_profile_screen),
    onClick: () -> Unit
) {
    val userInfo by viewModel.userInfo.collectAsState(UserInfo())
    IconButton(onClick = onClick) {
        AsyncImage(
            model = userInfo.url,
            contentDescription = contentDescription,
            modifier = modifier
        )
    }
}
