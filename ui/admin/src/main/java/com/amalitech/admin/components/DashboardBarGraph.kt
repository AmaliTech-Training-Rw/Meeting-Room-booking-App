package com.amalitech.admin.components

import android.annotation.SuppressLint
import android.view.LayoutInflater
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.amalitech.admin.R
import com.amalitech.admin.RoomsBookedTime
import com.amalitech.core_ui.theme.LocalSpacing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry


@SuppressLint("InflateParams")
@Composable
fun DashboardBarGraph(
    items: List<RoomsBookedTime>,
    modifier: Modifier = Modifier,
    backgroundColor: Int = MaterialTheme.colorScheme.background.toArgb(),
    barColor: Int = MaterialTheme.colorScheme.primary.toArgb(),
    selectedColor: Int = MaterialTheme.colorScheme.secondary.toArgb(),
    descriptionColor: Int = MaterialTheme.colorScheme.onBackground.toArgb(),
    maxNumberOnScreen: Float = MAX_NUMBER_OF_BAR_ON_THE_SCREEN,
    axisPosition: AxisPosition = AxisPosition.Bottom,
    animateXDuration: Int = 3000,
    animateYDuration: Int = 3000,
    setDrawValue: Boolean = true,
    titleTextStyle: TextStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold)
) {
    val spacing = LocalSpacing.current
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(1.dp)
            .shadow(1.dp)
            .padding(spacing.spaceMedium)
    ) {
        Text(
            stringResource(id = com.amalitech.core_ui.R.string.rooms_against_booked_time),
            style = titleTextStyle,
        )
        Spacer(Modifier.height(spacing.spaceMedium))
        AndroidView(
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.barchart, null, false)
                val chart = view.findViewById<BarChart>(R.id.chart1)
                val data = generateBarData(
                    barColor = barColor,
                    items = items.toMutableList(),
                    selectedColor = selectedColor,
                    contentColor = descriptionColor
                )

                chart.data = data
                chart.setBackgroundColor(backgroundColor)
                chart.animateXY(animateXDuration, animateYDuration)
                chart.setDescriptionColor(descriptionColor)
                chart.setVisibleXRangeMaximum(maxNumberOnScreen)
                chart.xAxis.position = when (axisPosition) {
                    AxisPosition.Bottom -> XAxis.XAxisPosition.BOTTOM
                    AxisPosition.Top -> XAxis.XAxisPosition.TOP
                }
                chart.xAxis.setLabelsToSkip(0)
                chart.xAxis.textColor = descriptionColor
                chart.axisLeft.textColor = descriptionColor
                chart.axisRight.textColor = descriptionColor
                chart.setDrawValueAboveBar(setDrawValue)
                chart.invalidate()

                view
            }
        )
    }
}

fun generateBarData(
    barColor: Int,
    items: MutableList<RoomsBookedTime>,
    selectedColor: Int,
    contentColor: Int
): BarData {
    val xValues = ArrayList<String>()
    val barEntries = ArrayList<BarEntry>()

    for (index in 0 until items.size) {
        xValues.add(items[index].roomName)
        barEntries.add(BarEntry(items[index].bookedTime, index))
    }

    val barDataSet = BarDataSet(barEntries, "")
    barDataSet.color = barColor
    barDataSet.valueTextColor = contentColor
    barDataSet.highLightColor = selectedColor
    barDataSet.barShadowColor = contentColor

    return BarData(xValues, barDataSet)
}

const val MAX_NUMBER_OF_BAR_ON_THE_SCREEN = 5f

enum class AxisPosition {
    Top,
    Bottom
}
