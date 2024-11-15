package com.awesome.manager.core.ui.actions.main

import com.awesome.manager.core.common.AmUIError
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.ui.actions.dynamic_fab.DynamicFabState
import com.awesome.manager.core.ui.actions.bottomsheet.BottomSheetState
import com.awesome.manager.core.ui.actions.error.ErrorState
import com.awesome.manager.core.ui.actions.navigation.NavigationState
import com.awesome.manager.core.ui.actions.picker.PickerState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


abstract class ActionsManager : NavigationState, DynamicFabState, BottomSheetState, ErrorState,
    PickerState {

    private val _refreshing: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val refreshing: MutableStateFlow<Boolean> = _refreshing
    fun startRefreshing() = _refreshing.update { true }
    fun endRefreshing() = _refreshing.update { false }

    suspend inline fun <T> Flow<AmUIState<T>>.processRequest(
        crossinline onSuccess: () -> Unit,
        crossinline onError: (AmUIError) -> Unit = { addError(it) }
    ) = collectLatest {
        when (it) {
            is AmUIState.Error -> onError(it.amUIError)
            is AmUIState.Loading -> dynamicFabLoading()
            is AmUIState.Success -> onSuccess()
        }
    }

    fun <T> Flow<AmUIState<T>>.processUIState() = onEach {
        when (it) {
            is AmUIState.Error -> addError(it.amUIError)
            is AmUIState.Loading -> dynamicFabLoading()
            is AmUIState.Success -> Unit
        }
    }.filterIsInstance<AmUIState.Success<T>>()

    private val _mainAction: MutableStateFlow<MainAction?> = MutableStateFlow(null)
    val mainAction: StateFlow<MainAction?> = _mainAction

    private fun updateAction(mainAction: MainAction) = _mainAction.update { mainAction }
    fun doneMainAction() = _mainAction.update { null }

    override fun NavigationAction.applyAction() = updateAction(this)
    override fun DynamicFabAction.applyAction() = updateAction(this)
    override fun BottomSheetAction.applyAction() = updateAction(this)
    override fun PickerAction.applyAction() = updateAction(this)
    override fun ErrorAction.applyAction() = updateAction(this)

    fun MainAction.applyMainAction() {
        when (this) {
            is DynamicFabAction -> applyAction()
            is BottomSheetAction -> applyAction()
            is NavigationAction -> applyAction()
            is PickerAction -> applyAction()
            is ErrorAction -> applyAction()
        }
    }

}
