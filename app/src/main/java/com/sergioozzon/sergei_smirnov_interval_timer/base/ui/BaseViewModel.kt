package com.sergioozzon.sergei_smirnov_interval_timer.base.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow

abstract class BaseViewModel<Wish, UiState, SideEffect> : ViewModel() {

    private val sideEffectChannel = Channel<SideEffect>(Channel.BUFFERED)
    val sideEffectFlow = sideEffectChannel.receiveAsFlow()

    val intentions = Channel<Wish>(Channel.UNLIMITED)

    fun sendWish(wish: Wish) {
        intentions.trySend(wish)
    }

    protected fun sendEffect(effect: SideEffect) {
        sideEffectChannel.trySend(effect)
    }

    protected abstract val _uiState: MutableStateFlow<UiState>
    val uiState: StateFlow<UiState>
        get() = _uiState.asStateFlow()


    init {
        intentions
            .consumeAsFlow()
            .map { reduce(wish = it, currentState = _uiState.value) }
            .onEach { _uiState.value = it }
            .launchIn(viewModelScope)
    }

    protected abstract fun reduce(wish: Wish, currentState: UiState): UiState

}