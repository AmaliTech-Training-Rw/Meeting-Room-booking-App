package com.amalitech.user.adduser

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.amalitech.core_ui.theme.LocalSpacing
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddUserScreen(
    showBottomSheet: Boolean,
    innerPadding: PaddingValues,
    viewModel: AddUserViewModel = koinViewModel()
) {

    val addUserState by viewModel.userUiState.collectAsStateWithLifecycle()
    val spacing = LocalSpacing.current
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()

    var isBottomSheetShown by remember { mutableStateOf(showBottomSheet) }

    ModalBottomSheet(
        onDismissRequest = {
            isBottomSheetShown = false
        },
        sheetState = sheetState
    ) {
        // Sheet content
        Button(onClick = {
            scope.launch { sheetState.hide() }.invokeOnCompletion {
                if (!sheetState.isVisible) {
                    isBottomSheetShown = false
                }
            }
        }) {
            Text("Hide bottom sheet")
        }
    }
}

