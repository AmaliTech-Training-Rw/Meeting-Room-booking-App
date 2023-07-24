package com.amalitech.admin.room.usecase

import com.amalitech.admin.room.Room
import com.amalitech.core_ui.util.SnackbarManager

// TODO: This is where we shall inject the repo(or some data source), after the room module merge
class AddRoom {
    operator fun invoke(
        room: Room
    ) = SnackbarManager.showMessage(com.amalitech.core.R.string.add_success)
}



