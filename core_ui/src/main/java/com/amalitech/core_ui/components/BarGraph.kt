package com.amalitech.core_ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.amalitech.core_ui.theme.Dimensions
import com.amalitech.core_ui.theme.LocalSpacing

@Composable
fun BarGraph(
    fillColor: Color,
    numberOfHorizontalBars: Int,
    item: RoomsBookedTime,
) {
    val spacing = LocalSpacing.current
    val linesColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 1f)

    BoxWithConstraints {
        val boxMaxHeight = this.maxHeight
        val horizontalBarHeight = getHorizontalBarHeight(boxMaxHeight, numberOfHorizontalBars)
        Canvas(
            modifier = Modifier
                .padding(spacing.spaceSmall)
                .drawBehind {
                    drawHorizontalLines(
                        numberOfHorizontalBars,
                        linesColor,
                        boxMaxHeight,
                        horizontalBarHeight,
                        spacing
                    )
                }
        ) {
            drawRect(
                topLeft = Offset(
                    0f,
                    boxMaxHeight.toPx() - (item.bookedTime / unitNumber) * horizontalBarHeight.toPx() - spacing.spaceLarge.toPx()
                ),
                size = Size(
                    spacing.spaceLarge.toPx(),
                    item.bookedTime / unitNumber * horizontalBarHeight.toPx()
                ),
                color = fillColor
            )
        }
        Box(
            Modifier.offset(
                0.dp,
                boxMaxHeight - spacing.spaceLarge
            )
        ) {
            Text(
                text = item.roomName,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(
                    spacing.spaceSmall
                )
            )
        }
    }
}

private fun DrawScope.drawHorizontalLines(
    numberOfHorizontalBars: Int,
    linesColor: Color,
    boxMaxHeight: Dp,
    horizontalBarHeight: Dp,
    spacing: Dimensions
) {
    (0 until numberOfHorizontalBars).forEach {
        drawLine(
            color = linesColor,
            start = Offset(
                0f,
                getHorizontalLineHeight(
                    boxMaxHeight.toPx(),
                    horizontalBarHeight.toPx(),
                    it,
                    spacing.spaceLarge.toPx()
                )
            ),
            end = Offset(
                2 * spacing.spaceLarge.toPx(),
                getHorizontalLineHeight(
                    boxMaxHeight.toPx(),
                    horizontalBarHeight.toPx(),
                    it,
                    spacing.spaceLarge.toPx()
                )
            )
        )
    }
}
