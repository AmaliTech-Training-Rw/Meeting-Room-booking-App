package com.amalitech.core_ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.LocalSpacing

fun DrawScope.dashboardBarGraph(
    offset: Offset,
    size: Size,
    fillColor: Color
) {
    drawRect(
        topLeft = offset,
        size = size,
        color = fillColor
    )
}

@Composable
fun DashboardGraph(
    roomsBookedTime: List<RoomsBookedTime>,
    modifier: Modifier = Modifier
) {
    val spacing = LocalSpacing.current
    val maxBookedTime = roomsBookedTime.maxOfOrNull { it.bookedTime } ?: 0
    val nextMultipleOfFive = getNextMultipleOfFive(maxBookedTime.toInt())
    val numberOfHorizontalBars = (nextMultipleOfFive / 5) + 1
    val linesColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 1f)
    val fillColor = MaterialTheme.colorScheme.primary

    Column(
        Modifier
            //.horizontalScroll(rememberScrollState())
            //.verticalScroll(rememberScrollState())
    ) {
        BoxWithConstraints(
            modifier = modifier
//            .horizontalScroll(rememberScrollState())
                .fillMaxWidth()
                .padding(vertical = spacing.spaceLarge)
        ) {
            val boxMaxHeight = maxHeight
            val horizontalBarHeight = boxMaxHeight / numberOfHorizontalBars

            (0 until numberOfHorizontalBars).forEach {
                Text(
                    text = it.toString(),
                    modifier = Modifier
                        .padding(spacing.spaceMedium)
                        .offset(
                            0.dp,
                            boxMaxHeight - horizontalBarHeight * it - (2 * spacing.spaceLarge.value).dp
                        )
                )
            }

            roomsBookedTime.forEachIndexed { index, room ->
                Text(
                    text = room.roomName,
                    modifier = Modifier
                        .width(spacing.spaceExtraLarge)
                        .offset(spacing.spaceExtraLarge * (index + 1), boxMaxHeight),
                    overflow = TextOverflow.Ellipsis
                )
            }

            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .drawBehind {
                        val canvasWidth = size.width
                        val canvasHeight = size.height

                        (0 until numberOfHorizontalBars).forEach {
                            drawLine(
                                color = linesColor,
                                start = Offset(
                                    spacing.spaceLarge.toPx(),
                                    canvasHeight - (horizontalBarHeight * it + spacing.spaceLarge).toPx()
                                ),
                                end = Offset(
                                    canvasWidth - spacing.spaceLarge.toPx(),
                                    canvasHeight - (horizontalBarHeight * it + spacing.spaceLarge).toPx()
                                )
                            )
                        }
                    }
            ) {
                roomsBookedTime.forEachIndexed { index, room ->
                    dashboardBarGraph(
                        Offset(
                            (spacing.spaceExtraLarge * (index + 1)).toPx(),
                            boxMaxHeight.toPx() - (room.bookedTime / 5) * horizontalBarHeight.toPx() - spacing.spaceLarge.toPx()//- horizontalBarHeight.toPx() //+ spacing.spaceLarge.toPx()
                        ),
                        size = Size(
                            spacing.spaceLarge.toPx(),
                            room.bookedTime / 5 * horizontalBarHeight.toPx()//room.bookedTime / 5
                        ),
                        fillColor = fillColor,
                    )
                }
            }
        }
    }
}


fun getNextMultipleOfFive(number: Int): Int {
    val remainder = number % 5
    return if (remainder == 0) {
        number  // Already a multiple of 5
    } else {
        number + (5 - remainder)  // Calculate the next multiple of 5
    }
}

@Preview
@Composable
fun Pre() {
    val fillColor = MaterialTheme.colorScheme.primary
    Canvas(modifier = Modifier) {
        dashboardBarGraph(
            offset = Offset(0f, size.height + 2f),
            size = Size(width = 12.dp.toPx(), 20.dp.toPx()),
            fillColor = fillColor
        )
    }
}
