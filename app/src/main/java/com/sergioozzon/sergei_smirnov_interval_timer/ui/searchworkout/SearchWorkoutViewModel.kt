package com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.sergioozzon.sergei_smirnov_interval_timer.base.network.NotFoundError
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.BaseViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.data.usecases.GetWorkoutUseCase
import com.sergioozzon.sergei_smirnov_interval_timer.domain.WorkoutDTO
import com.sergioozzon.sergei_smirnov_interval_timer.ui.WorkoutSharedViewModel
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.SearchWorkoutViewModel.SideEffect
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.SearchWorkoutViewModel.UiState
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.SearchWorkoutViewModel.Wish
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val DEFAULT_WORKOUT_ID = "68"

@SuppressLint("StaticFieldLeak")
class SearchWorkoutViewModel(
    private val getWorkoutUseCase: GetWorkoutUseCase,
    private val workoutSharedViewModel: WorkoutSharedViewModel
) : BaseViewModel<Wish, UiState, SideEffect>() {

    override val _uiState = MutableStateFlow(UiState())

    override fun reduce(
        wish: Wish,
        currentState: UiState,
    ): UiState {
        return when (wish) {
            is Wish.GetWorkout -> {
                getWorkout(currentState.inputValue)
                currentState.copy(
                    loading = true,
                    error = null
                )
            }

            is Wish.HandleFailure -> {
                currentState.copy(
                    error = wish.error,
                    loading = false
                )
            }

            is Wish.HandleSuccess -> {
                workoutSharedViewModel.setWorkout(wish.workoutDTO)
                sendEffect(SideEffect.GoWorkoutScreen(wish.workoutDTO))
                currentState.copy(
                    loading = false
                )
            }

            is Wish.UpdateState -> {
                currentState.copy(
                    error = null,
                    inputValue = wish.inputValue
                )
            }
        }
    }

    private fun getWorkout(workOutId: String) {
        viewModelScope.launch {
            getWorkoutUseCase.invoke(workOutId)
                .onSuccess { workoutDTO ->
                    sendWish(Wish.HandleSuccess(workoutDTO))
                }
                .onFailure { throwable ->
                    sendWish(Wish.HandleFailure(throwable.toUiError()))
                }
        }
    }

    private fun Throwable.toUiError(): SearchWorkoutError {
        return when (this) {
            is NotFoundError -> SearchWorkoutError.NOT_FOUND
            else -> SearchWorkoutError.UNKNOWN
        }
    }

    sealed interface Wish {
        class GetWorkout() : Wish
        class HandleSuccess(val workoutDTO: WorkoutDTO) : Wish
        class HandleFailure(val error: SearchWorkoutError) : Wish
        class UpdateState(val inputValue: String) : Wish
    }

    data class UiState(
        val loading: Boolean = false,
        val error: SearchWorkoutError? = null,
        val inputValue: String = DEFAULT_WORKOUT_ID,
    )

    sealed interface SideEffect {
        class GoWorkoutScreen(val workoutDTO: WorkoutDTO) : SideEffect
    }

    enum class SearchWorkoutError {
        NOT_FOUND,
        UNKNOWN,
    }

}
