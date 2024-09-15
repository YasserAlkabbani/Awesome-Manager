package com.awesome.manager.core.designsystem.actions.main

import com.awesome.manager.core.designsystem.actions.appbar.DynamicBarState
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetState
import com.awesome.manager.core.designsystem.actions.navigation.NavigationState
import com.awesome.manager.core.designsystem.actions.picker.PickerState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


abstract class StateManager : NavigationState, DynamicBarState, BottomSheetState, PickerState {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    fun setLoading(loading: Boolean) {
        _isLoading.update { loading }
    }

    suspend fun (suspend () -> Unit).processWitLoading() {
        setLoading(true)
        this.invoke()
        setLoading(false)
    }

    private val _mainAction: MutableStateFlow<MainAction?> = MutableStateFlow(null)
    val mainAction: StateFlow<MainAction?> = _mainAction
    private fun updateAction(mainAction: MainAction) {
        _mainAction.update { mainAction }
    }

    fun doneMainAction() {
        _mainAction.update { null }
    }

    override fun NavigationAction.applyAction() = updateAction(this)
    override fun DynamicBarAction.applyAction() = updateAction(this)
    override fun BottomSheetAction.applyAction() = updateAction(this)
    override fun PickerAction.applyAction() = updateAction(this)
    fun MainAction.applyMainAction() = when (this) {
        is DynamicBarAction -> applyAction()
        is BottomSheetAction -> applyAction()
        is NavigationAction -> applyAction()
        is PickerAction -> applyAction()
    }

}
