package com.amalitech.core_ui.util

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalPermissionsApi::class)
fun requestImagePermission(
    permissionState: MultiplePermissionsState,
    onShouldShowRational: (Boolean) -> Unit,
    onOpenSettings: (Boolean) -> Unit
) {
    permissionState.launchMultiplePermissionRequest()
    permissionState.permissions.forEach {
        when (it.permission) {
            Manifest.permission.READ_MEDIA_IMAGES -> {
                when {
                    it.hasPermission -> return
                    permissionState.shouldShowRationale -> {
                        onShouldShowRational(true)
                    }
                    !permissionState.shouldShowRationale && !it.hasPermission -> {
                        onOpenSettings(true)
                    }
                }
            }
        }
    }
}
