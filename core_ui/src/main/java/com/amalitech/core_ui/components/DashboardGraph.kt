package com.amalitech.core_ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.R
import com.amalitech.core_ui.theme.Dimensions
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun DashboardGraph(
    items: List<RoomsBookedTime>,
    modifier: Modifier = Modifier,
    fillColor: Color = MaterialTheme.colorScheme.primary,
    horizontalLinesColor: Color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
    roomsNameColor: Color = MaterialTheme.colorScheme.onBackground,
    backgroundColor: Color = MaterialTheme.colorScheme.background
) {
    val spacing = LocalSpacing.current
    val maxBookedTime = items.maxOfOrNull { it.bookedTime } ?: 0
    val nextMultipleOfUnit = getNextMultipleOfUnit(maxBookedTime.toInt())
    val numberOfHorizontalBars = (nextMultipleOfUnit / unitNumber) + 1

    Column(
        modifier
            .padding(2.dp)
            .shadow(
                elevation = 2.dp
            )
            .padding(spacing.spaceMedium)
            .background(backgroundColor),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.rooms_against_booked_time),
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
        )
        Spacer(Modifier.height(spacing.spaceMedium))

        LazyRow(Modifier.fillMaxHeight()) {
            item {
                BoxWithConstraints {
                    val boxMaxHeight = maxHeight
                    val horizontalBarHeight = getHorizontalBarHeight(boxMaxHeight, numberOfHorizontalBars)
                    AddSideNumbers(numberOfHorizontalBars, spacing, boxMaxHeight, horizontalBarHeight, horizontalLinesColor)
                }

            }
            items(items.size) { index ->
                BarGraph(
                    fillColor = fillColor,
                    numberOfHorizontalBars = numberOfHorizontalBars,
                    items[index],
                    horizontalLinesColor,
                    roomsNameColor
                )
            }
        }
    }
}

@Composable
private fun AddSideNumbers(
    numberOfHorizontalBars: Int,
    spacing: Dimensions,
    boxMaxHeight: Dp,
    horizontalBarHeight: Dp,
    horizontalLinesColor: Color,
) {
    (0 until numberOfHorizontalBars).forEach {
        Text(
            text = (it * unitNumber).toString(),
            modifier = Modifier
                .padding(spacing.spaceSmall)
                .offset(
                    0.dp,
                    getHorizontalLineHeight(
                        boxMaxHeight.value,
                        horizontalBarHeight.value,
                        it,
                        spacing.spaceLarge.value + spacing.spaceMedium.value
                    ).dp
                ),
            color = horizontalLinesColor
        )
    }
}

fun getHorizontalBarHeight(
    maxHeight: Dp,
    numberOfHorizontalBars: Int
): Dp {
    return maxHeight / numberOfHorizontalBars
}

fun getHorizontalLineHeight(
    boxMaxHeight: Float,
    horizontalBarHeight: Float,
    numberOfBarsItem: Int,
    spacing: Float
): Float {
    return boxMaxHeight - (horizontalBarHeight * numberOfBarsItem + spacing)
}


fun getNextMultipleOfUnit(number: Int): Int {
    val remainder = number % unitNumber
    return if (remainder == 0) {
        number  // Already a multiple of unit
    } else {
        number + (unitNumber - remainder)  // Calculate the next multiple of unit
    }
}

const val unitNumber = 15
