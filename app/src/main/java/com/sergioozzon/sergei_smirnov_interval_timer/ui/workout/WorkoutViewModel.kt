package com.sergioozzon.sergei_smirnov_interval_timer.ui.workout

import androidx.lifecycle.viewModelScope
import com.sergioozzon.sergei_smirnov_interval_timer.base.sound.WorkoutSoundService
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.BaseViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.domain.WorkoutDTO
import com.sergioozzon.sergei_smirnov_interval_timer.ui.workout.components.TimerState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val workoutSoundService: WorkoutSoundService,
) :
    BaseViewModel<WorkoutViewModel.Wish, WorkoutViewModel.UiState, WorkoutViewModel.SideEffect>() {

    override val _uiState = MutableStateFlow(UiState())

    private var timerJob: Job? = null

    override fun reduce(
        wish: Wish,
        currentState: UiState,
    ): UiState {
        return when (wish) {
            is Wish.InitWorkout -> {
                val totalTimeSeconds = wish.workout.timer?.totalTime ?: 0
                val intervalTimeSeconds =
                    wish.workout.timer?.intervals[currentState.currentIntervalIndex]?.time ?: 0

                currentState.copy(
                    workout = wish.workout,
                    timerState = TimerState.IDLE,
                    totalTimeSeconds = totalTimeSeconds,
                    intervalTimeSeconds = intervalTimeSeconds,
                    formattedTotalTime = formatTime(totalTimeSeconds),
                    formattedInitiallyTotalTime = formatTime(totalTimeSeconds),
                    formattedIntervalTime = formatTime(intervalTimeSeconds),
                    currentInterval = wish.workout.timer?.intervals[currentState.currentIntervalIndex]?.title
                        ?: ""
                )
            }

            is Wish.StartTimer -> {
                if (currentState.timerState == TimerState.IDLE) {
                    workoutSoundService.playWorkoutStart()
                }
                startTimer()
                currentState.copy(timerState = TimerState.RUNNING)
            }

            is Wish.PauseTimer -> {
                pauseTimer()
                currentState.copy(timerState = TimerState.PAUSED)
            }

            is Wish.ResetTimer -> {
                resetTimer()

                val totalTimeSeconds = currentState.workout?.timer?.totalTime ?: 0
                val intervalTimeSeconds = currentState.workout?.timer?.intervals[0]?.time ?: 0

                currentState.copy(
                    workout = currentState.workout,
                    timerState = TimerState.IDLE,
                    totalTimeSeconds = totalTimeSeconds,
                    intervalTimeSeconds = intervalTimeSeconds,
                    formattedTotalTime = formatTime(totalTimeSeconds),
                    formattedInitiallyTotalTime = formatTime(totalTimeSeconds),
                    formattedIntervalTime = formatTime(intervalTimeSeconds),
                    currentIntervalIndex = 0,
                    currentInterval = currentState.workout?.timer?.intervals[0]?.title ?: "",
                    intervalProgress = 0f,
                    totalProgress = 0f
                )
            }

            is Wish.Tick -> {


                val newTotalTimeSeconds = currentState.totalTimeSeconds - 1
                var newIntervalTimeSeconds = currentState.intervalTimeSeconds - 1

                val totalProgress =
                    1f - newTotalTimeSeconds.toFloat() / currentState.workout?.timer?.totalTime!!

                val totalIntervalTime =
                    currentState.workout.timer.intervals[currentState.currentIntervalIndex].time.toFloat()

                var intervalProgress = 1f
                var newCurrentIntervalIndex: Int

                val isWorkoutCompleted = newTotalTimeSeconds == 0
                val isIntervalCompleted = newIntervalTimeSeconds == 0


                if (isWorkoutCompleted) {
                    resetTimer()
                    workoutSoundService.playWorkoutFinished()

                    currentState.copy(
                        timerState = TimerState.COMPLETED,
                        totalTimeSeconds = newTotalTimeSeconds,
                        intervalTimeSeconds = newIntervalTimeSeconds,
                        formattedTotalTime = formatTime(newTotalTimeSeconds),
                        formattedIntervalTime = formatTime(newIntervalTimeSeconds),
                        formattedTotalTimePassed = formatTime(currentState.workout.timer.totalTime),
                        totalProgress = totalProgress,
                        intervalProgress = intervalProgress,
                    )
                } else {

                    if (isIntervalCompleted) {
                        workoutSoundService.playNextInterval()
                        newCurrentIntervalIndex = currentState.currentIntervalIndex + 1
                        newIntervalTimeSeconds =
                            currentState.workout.timer.intervals[newCurrentIntervalIndex].time
                        intervalProgress = 0f
                    } else {
                        newCurrentIntervalIndex = currentState.currentIntervalIndex
                        intervalProgress = 1f - newIntervalTimeSeconds.toFloat() / totalIntervalTime
                    }

                    currentState.copy(
                        timerState = TimerState.RUNNING,
                        totalTimeSeconds = newTotalTimeSeconds,
                        intervalTimeSeconds = newIntervalTimeSeconds,
                        formattedTotalTime = formatTime(newTotalTimeSeconds),
                        formattedIntervalTime = formatTime(newIntervalTimeSeconds),
                        formattedTotalTimePassed = formatTime(currentState.workout.timer.totalTime - newTotalTimeSeconds),
                        totalProgress = totalProgress,
                        intervalProgress = intervalProgress,
                        currentIntervalIndex = newCurrentIntervalIndex,
                        currentInterval = currentState.workout.timer.intervals[newCurrentIntervalIndex].title
                    )
                }
            }
        }
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            while (true) {
                delay(1000)
                sendWish(Wish.Tick)
            }
        }
    }

    private fun pauseTimer() {
        timerJob?.cancel()
    }

    private fun resetTimer() {
        timerJob?.cancel()
    }

    private fun formatTime(seconds: Int): String {
        val minutes = seconds / 60
        val remainingSeconds = seconds % 60
        return "%02d:%02d".format(minutes, remainingSeconds)
    }

    sealed interface Wish {
        data class InitWorkout(val workout: WorkoutDTO) : Wish
        object StartTimer : Wish
        object PauseTimer : Wish
        object ResetTimer : Wish
        object Tick : Wish
    }

    data class UiState(
        val workout: WorkoutDTO? = null,
        val timerState: TimerState = TimerState.IDLE,
        val totalTimeSeconds: Int = 0,
        val intervalTimeSeconds: Int = 0,
        val formattedTotalTime: String = "00:00",
        val formattedInitiallyTotalTime: String = "00:00",
        val formattedIntervalTime: String = "00:00",
        val formattedTotalTimePassed: String = "00:00",
        val currentIntervalIndex: Int = 0,
        val currentInterval: String = "",
        val totalProgress: Float = 0f,
        val intervalProgress: Float = 0f,
    )

    sealed interface SideEffect {
        object WorkoutFinished : SideEffect
    }
}
