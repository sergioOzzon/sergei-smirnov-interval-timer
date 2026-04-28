package com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.sergioozzon.sergei_smirnov_interval_timer.R
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.IntervalTheme
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Orange
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Secondary
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.components.GhostButton
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.components.PrimaryButton

@Composable
fun WorkoutControlButtons(
    timerState: TimerState,
    onStartClick: () -> Unit,
    onPauseClick: () -> Unit,
    onResumeClick: () -> Unit,
    onRestartClick: () -> Unit,
    onResetClick: () -> Unit,
    onNewWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {
        when (timerState) {
            TimerState.IDLE -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_start),
                    onClick = onStartClick
                )
            }

            TimerState.RUNNING -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_pause),
                    onClick = onPauseClick,
                    color = Orange
                )
            }

            TimerState.PAUSED -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_resume),
                    onClick = onResumeClick
                )
            }

            TimerState.COMPLETED -> {
                PrimaryButton(
                    text = stringResource(R.string.workout_button_again),
                    onClick = onRestartClick,
                    color = Secondary
                )
            }
        }

        Spacer(modifier = Modifier.height(IntervalTheme.spacing.m))

        GhostButton(
            text = if (timerState == TimerState.COMPLETED) {
                stringResource(R.string.workout_button_new)
            } else {
                stringResource(R.string.workout_button_reset)
            },
            onClick = if (timerState == TimerState.COMPLETED) onNewWorkoutClick else onResetClick
        )
    }
}
