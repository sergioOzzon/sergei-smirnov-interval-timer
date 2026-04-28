package com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.items.IntervalState
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Border
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.DisabledText
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Orange
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Primary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Secondary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Surface
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextSecondary


@Composable
fun TimerContainer(
    state: TimerState,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit,
) {
    val (borderColor, backgroundBrush) = when (state) {
        TimerState.IDLE -> Border to SolidColor(Color.White)
        TimerState.RUNNING -> MaterialTheme.colorScheme.primary to Brush.verticalGradient(
            listOf(Primary.copy(alpha = 0.04f), Color.White)
        )

        TimerState.PAUSED -> Orange to Brush.verticalGradient(
            listOf(Orange.copy(alpha = 0.04f), Color.White)
        )

        TimerState.COMPLETED -> Secondary to Brush.verticalGradient(
            listOf(Secondary.copy(alpha = 0.04f), Color.White)
        )
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundBrush)
            .border(0.5.dp, borderColor, RoundedCornerShape(16.dp))
            .fillMaxWidth()
    ) {
        Column { content() }
    }
}

@Composable
fun NumberBadge(
    number: Int,
    state: IntervalState,
) {
    Box(
        modifier = Modifier
            .size(28.dp)
            .background(
                when (state) {
                    IntervalState.IDLE -> DisabledText
                    IntervalState.RUNNING -> Primary
                    IntervalState.PAUSED -> Orange
                    else -> Color.Transparent
                }, CircleShape
            ),

        contentAlignment = Alignment.Center
    ) {
        if (state == IntervalState.COMPLETED) Icon(
            painterResource(android.R.drawable.checkbox_on_background),
            contentDescription = null,
        )
        else Text(
            text = number.toString(),
            style = MaterialTheme.typography.labelMedium,
            color = when (state) {
                IntervalState.IDLE -> TextSecondary
                IntervalState.RUNNING -> Surface
                IntervalState.PAUSED -> Surface
                else -> Color.Transparent
            }
        )
    }
}

@Composable
fun WorkoutProgressBar(
    progress: Float,
    state: TimerState,
    modifier: Modifier = Modifier,
) {
    val color = when (state) {
        TimerState.RUNNING -> Primary
        TimerState.PAUSED -> Orange
        TimerState.COMPLETED -> Secondary
        else -> Border
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(4.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(Border),
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(fraction = progress)
                .clip(RoundedCornerShape(2.dp))
                .background(color)
        )
    }
}
