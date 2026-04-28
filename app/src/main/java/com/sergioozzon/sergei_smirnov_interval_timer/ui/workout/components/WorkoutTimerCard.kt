package com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.sergioozzon.sergei_smirnov_interval_timer.R
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.IntervalTheme
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Orange
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Primary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Secondary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextPrimary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextSecondary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextTertiary

@Composable
fun WorkoutTimerCard(
    timerState: TimerState,
    currentInterval: String,
    formattedTotalTime: String,
    formattedIntervalTime: String,
    formattedTotalTimePassed: String,
    formattedInitiallyTotalTime: String,
    totalProgress: Float,
    modifier: Modifier = Modifier,
) {
    TimerContainer(
        state = timerState,
        modifier = modifier.defaultMinSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = when (timerState) {
                    TimerState.IDLE -> stringResource(R.string.workout_state_idle)
                    TimerState.RUNNING -> stringResource(R.string.workout_state_running)
                    TimerState.PAUSED -> stringResource(R.string.workout_state_pause)
                    TimerState.COMPLETED -> stringResource(R.string.workout_state_completed)
                }.uppercase(),
                style = MaterialTheme.typography.titleMedium,
                color = when (timerState) {
                    TimerState.IDLE -> TextTertiary
                    TimerState.RUNNING -> Primary
                    TimerState.PAUSED -> Orange
                    TimerState.COMPLETED -> Secondary
                },
                modifier = Modifier.padding(top = IntervalTheme.spacing.xxl)
            )

            Text(
                text = currentInterval,
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )

            Text(
                text = if (timerState == TimerState.IDLE) formattedTotalTime else formattedIntervalTime,
                style = MaterialTheme.typography.displayLarge,
                fontWeight = FontWeight.Bold,
                color = TextPrimary,
                modifier = Modifier.padding(vertical = IntervalTheme.spacing.s)
            )

            Text(
                text = if (timerState == TimerState.IDLE) {
                    stringResource(R.string.workout_timer_total)
                } else {
                    stringResource(
                        R.string.workout_timer_progress,
                        formattedTotalTimePassed,
                        formattedInitiallyTotalTime
                    )
                },
                style = MaterialTheme.typography.bodyLarge,
                color = TextSecondary
            )

            WorkoutProgressBar(
                progress = totalProgress,
                state = timerState,
                modifier = Modifier.padding(IntervalTheme.spacing.xxl)
            )
        }
    }
}
