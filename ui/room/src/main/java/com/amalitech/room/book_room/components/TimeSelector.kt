package com.amalitech.room.book_room.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing
import com.amalitech.core_ui.util.formatDate
import com.amalitech.ui.room.R
import com.amalitech.room.book_room.TimeUi
import java.time.LocalDate
import java.time.LocalTime

@Composable
fun TimeSelector(
    availableTimes: List<TimeUi>,
    onDismiss: () -> Unit,
    onTimeSelected: (selectedTime: LocalTime) -> Unit,
    selectedDate: LocalDate?,
    modifier: Modifier = Modifier,
    selectedTime: LocalTime? = null
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onDismiss,
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_close_24),
                    contentDescription = stringResource(id = R.string.cancel)
                )
            }
            Spacer(Modifier.width(spacing.spaceMedium))
            Text(
                text = stringResource(id = R.string.select_time),
                style = MaterialTheme.typography.headlineMedium
            )
        }
        Spacer(Modifier.height(spacing.spaceMedium))
        val dateText = selectedDate?.let { formatDate(it) } ?: ""
        Text(
            text = stringResource(id = R.string.selected_date, dateText),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(Modifier.height(spacing.spaceMedium))
        LazyVerticalGrid(
            columns = GridCells.Adaptive(spacing.spaceExtraLarge),
            verticalArrangement = Arrangement.spacedBy(spacing.spaceSmall),
            horizontalArrangement = Arrangement.spacedBy(spacing.spaceExtraSmall)
        ) {
            items(availableTimes) { timeUi ->
                val localTime = timeUi.time
                TimeItem(
                    time = localTime,
                    onClick = {
                        onTimeSelected(localTime)
                        onDismiss()
                    },
                    isSelected = localTime == selectedTime,
                    modifier = Modifier
                        .height(40.dp)
                        .width(spacing.spaceExtraLarge),
                    enabled = timeUi.isAvailable
                )
            }
        }
    }
}
