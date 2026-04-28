package com.sergioozzon.sergei_smirnov_interval_timer.ui.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sergioozzon.sergei_smirnov_interval_timer.R
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.items.IntervalItem
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.items.IntervalState
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Bg
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Border
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.IntervalTheme
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Surface
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextPrimary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextSecondary
import com.sergioozzon.sergei_smirnov_interval_timer.domain.TimerDTO
import com.sergioozzon.sergei_smirnov_interval_timer.domain.WorkoutDTO
import com.sergioozzon.sergei_smirnov_interval_timer.ui.WorkoutSharedViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components.TimerState
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components.WorkoutAppbar
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components.WorkoutControlButtons
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components.WorkoutTimerCard
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun WorkoutScreen(
    navController: NavController,
    viewModel: WorkoutViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val sharedViewModel: WorkoutSharedViewModel = koinViewModel()
    val workout by sharedViewModel.workout.collectAsState()

    LaunchedEffect(workout) {
        workout?.let {
            viewModel.sendWish(WorkoutViewModel.Wish.InitWorkout(it))
        }
    }

    WorkoutScreenContent(
        uiState = uiState,
        onAction = viewModel::sendWish,
        onBackAction = { navController.popBackStack() })

}

@Composable
fun WorkoutScreenContent(
    uiState: WorkoutViewModel.UiState,
    onAction: (WorkoutViewModel.Wish) -> Unit,
    onBackAction: () -> Unit,
) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Bg)
            .fillMaxSize()
            .padding(horizontal = IntervalTheme.spacing.xxl, vertical = IntervalTheme.spacing.l),
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        WorkoutAppbar(
            title = uiState.workout?.timer?.title ?: "",
            time = uiState.formattedTotalTime,
            timerState = uiState.timerState,
            onBackClick = {
                onBackAction()
            })

        WorkoutTimerCard(
            timerState = uiState.timerState,
            currentInterval = uiState.currentInterval,
            formattedTotalTime = uiState.formattedTotalTime,
            formattedIntervalTime = uiState.formattedIntervalTime,
            formattedTotalTimePassed = uiState.formattedTotalTimePassed,
            formattedInitiallyTotalTime = uiState.formattedInitiallyTotalTime,
            totalProgress = uiState.totalProgress,
            modifier = Modifier.padding(
                top = IntervalTheme.spacing.l,
            )
        )

        if (uiState.timerState == TimerState.COMPLETED) {
            val intervalsCount = uiState.workout?.timer?.intervals?.size ?: 0
            Row(
                horizontalArrangement = Arrangement.spacedBy(IntervalTheme.spacing.l),
                modifier = Modifier
                    .padding(top = IntervalTheme.spacing.xl)
                    .fillMaxWidth()
            ) {
                WorkoutSummaryItem(
                    value = uiState.formattedInitiallyTotalTime,
                    label = stringResource(R.string.workout_timer_total),
                    modifier = Modifier.weight(1f)
                )
                WorkoutSummaryItem(
                    value = intervalsCount.toString(), label = pluralStringResource(
                        R.plurals.workout_intervals_count_label, intervalsCount
                    ), modifier = Modifier.weight(1f)
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                .padding(
                    top = IntervalTheme.spacing.xl, bottom = IntervalTheme.spacing.s
                )
                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = stringResource(R.string.workout_intervals_title),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.SemiBold,
                color = TextSecondary
            )

            Text(
                text = if (uiState.timerState == TimerState.IDLE) pluralStringResource(
                    R.plurals.workout_intervals_total,
                    uiState.workout?.timer?.intervals?.size ?: 1,
                    uiState.workout?.timer?.intervals?.size ?: 1
                )
                else if (uiState.timerState == TimerState.COMPLETED) {
                    stringResource(
                        R.string.workout_intervals_step_completed,
                        uiState.currentIntervalIndex + 1,
                        uiState.workout?.timer?.intervals?.size ?: 1
                    )
                } else {
                    stringResource(
                        R.string.workout_intervals_step,
                        uiState.currentIntervalIndex + 1,
                        uiState.workout?.timer?.intervals?.size ?: 1
                    )
                }, style = MaterialTheme.typography.bodyMedium, color = TextSecondary
            )
        }

        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.workout?.timer?.intervals?.size ?: 0) { key ->
                IntervalItem(
                    name = uiState.workout?.timer?.intervals?.get(key)?.title ?: "",
                    order = key + 1,
                    duration = if (key == uiState.currentIntervalIndex) uiState.formattedIntervalTime
                    else formatTime(
                        uiState.workout?.timer?.intervals?.get(key)?.time ?: 0
                    ),
                    state = getIntervalState(uiState, key),
                    progress = uiState.intervalProgress,
                    modifier = Modifier.padding(vertical = IntervalTheme.spacing.xs)
                )
            }
        }

        WorkoutControlButtons(
            timerState = uiState.timerState,
            onStartClick = { onAction(WorkoutViewModel.Wish.StartTimer) },
            onPauseClick = { onAction(WorkoutViewModel.Wish.PauseTimer) },
            onResumeClick = { onAction(WorkoutViewModel.Wish.StartTimer) },
            onRestartClick = { onAction(WorkoutViewModel.Wish.ResetTimer) },
            onResetClick = { onAction(WorkoutViewModel.Wish.ResetTimer) },
            onNewWorkoutClick = { onBackAction.invoke() },
        )

    }

}

@Composable
private fun WorkoutSummaryItem(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Surface)
            .border(0.5.dp, Border, RoundedCornerShape(12.dp))
            .padding(vertical = IntervalTheme.spacing.l)
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = TextPrimary
        )
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            color = TextSecondary,
            modifier = Modifier.padding(top = IntervalTheme.spacing.xs)
        )
    }
}

fun getIntervalState(uiState: WorkoutViewModel.UiState, key: Int): IntervalState {
    if (uiState.timerState == TimerState.COMPLETED) {
        return IntervalState.DONE // for change state all of intervals
    }

    return if (uiState.currentIntervalIndex == key) {
        when (uiState.timerState) {
            TimerState.IDLE -> IntervalState.RUNNING
            TimerState.RUNNING -> IntervalState.RUNNING
            TimerState.PAUSED -> IntervalState.PAUSED
        }
    } else if (uiState.currentIntervalIndex < key) {
        IntervalState.IDLE
    } else {
        IntervalState.COMPLETED
    }
}

private fun formatTime(seconds: Int): String {
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun WorkoutScreenPreview() {
    WorkoutScreenContent(
        uiState = WorkoutViewModel.UiState(
            workout = WorkoutDTO(
                timer = TimerDTO(
                    id = 1, title = "Тренировка 1", totalTime = 120, intervals = listOf()
                )
            ),
            formattedTotalTime = "12:15",
            formattedInitiallyTotalTime = "15:00",
            timerState = TimerState.IDLE,
            currentInterval = "Медленный бег"
        ), onAction = {}, onBackAction = {})
}
