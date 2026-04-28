package com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.dropShadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.shadow.Shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.sergioozzon.sergei_smirnov_interval_timer.R
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Bg
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.IntervalTheme.spacing
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.Primary
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.PrimaryLight
import com.sergioozzon.sergei_smirnov_interval_timer.base.ui.theme.TextSecondary
import com.sergioozzon.sergei_smirnov_interval_timer.navigation.Destination
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.components.PrimaryButton
import com.sergioozzon.sergei_smirnov_interval_timer.ui.searchworkout.components.SearchWorkoutInputField
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SearchWorkoutScreen(
    navController: NavController,
    viewModel: SearchWorkoutViewModel = koinViewModel(),
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val onAction = remember(viewModel) { viewModel::sendWish }
    SearchWorkoutScreenContent(uiState, onAction)

    LaunchedEffect(Unit) {
        viewModel
            .sideEffectFlow
            .onEach { effect ->
                when (effect) {
                    is SearchWorkoutViewModel.SideEffect.GoWorkoutScreen -> navController.navigate((Destination.Workout))
                }
            }
            .launchIn(this)
    }

}


@Composable
private fun SearchWorkoutScreenContent(
    uiState: SearchWorkoutViewModel.UiState,
    onAction: (SearchWorkoutViewModel.Wish) -> Unit,
) {
    val onLoadWorkout = remember(onAction) {
        { onAction(SearchWorkoutViewModel.Wish.GetWorkout()) }
    }
    val onInputValueChange: (String) -> Unit = remember(onAction) {
        { value -> onAction(SearchWorkoutViewModel.Wish.UpdateState(value)) }
    }
    val keyboardActions = remember(onLoadWorkout) {
        KeyboardActions {
            onLoadWorkout()
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(Bg)
            .padding(horizontal = spacing.xxl, vertical = 0.dp)
            .fillMaxSize()
    ) {
        Spacer(modifier = Modifier.padding(top = spacing.xxl))
        Box(
            modifier = Modifier
                .padding(top = spacing.xxl)
                .background(
                    color = Primary,
                    shape = RoundedCornerShape(16.dp)
                )
                .dropShadow(
                    shape = RoundedCornerShape(16.dp),
                    shadow = Shadow(
                        10.dp,
                        color = PrimaryLight.copy(alpha = 0.15f),
                        offset = DpOffset(x = 0.dp, y = 10.dp)
                    )
                )
                .size(64.dp),
            contentAlignment = Alignment.Center
        ) {
            Image(
                modifier = Modifier
                    .size(32.dp),
                painter = painterResource(R.drawable.ic_clock),
                colorFilter = ColorFilter.tint(Color.White),
                contentDescription = "",
                alignment = Alignment.Center,
            )
        }

        Text(
            text = stringResource(R.string.search_title),
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier
                .absoluteOffset()
                .padding(top = spacing.xxl),
            textAlign = TextAlign.Center
        )
        Text(
            text = stringResource(R.string.search_description),
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            color = TextSecondary,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = spacing.l)
        )

        SearchWorkoutInputField(
            modifier = Modifier.padding(top = spacing.xl),
            value = uiState.inputValue,
            label = stringResource(R.string.search_id_label),
            placeholder = stringResource(R.string.search_id_label),
            errorText = getErrorTextBy(uiState.error),
            isEnabled = !uiState.loading,
            onValueChange = onInputValueChange,
            keyboardAction = keyboardActions
        )

        PrimaryButton(
            text = getButtonTextBy(uiState),
            onClick = onLoadWorkout,
            modifier = Modifier.padding(top = spacing.l),
            enabled = !uiState.loading,
            isLoading = uiState.loading
        )
    }

}

@Composable
private fun getButtonTextBy(uiState: SearchWorkoutViewModel.UiState): String {
    return if (uiState.error != null)
        stringResource(R.string.search_button_retry)
    else if (uiState.loading)
        stringResource(R.string.search_button_loading)
    else
        stringResource(R.string.search_button_load)
}

@Composable
private fun getErrorTextBy(error: SearchWorkoutViewModel.SearchWorkoutError?): String? {
    return when (error) {
        SearchWorkoutViewModel.SearchWorkoutError.NOT_FOUND -> {
            stringResource(R.string.search_error_not_found)
        }

        SearchWorkoutViewModel.SearchWorkoutError.UNKNOWN -> {
            stringResource(R.string.search_error_unknown)
        }

        null -> null
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
fun SearchWorkoutLayoutPreview() {
    SearchWorkoutScreenContent(
        SearchWorkoutViewModel.UiState(
            loading = false,
            error = SearchWorkoutViewModel.SearchWorkoutError.NOT_FOUND
        )
    ) { }
}
