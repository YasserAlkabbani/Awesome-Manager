package com.awesome.manager.core.designsystem.actions.main

import com.awesome.manager.core.designsystem.actions.appbar.AppBarState
import com.awesome.manager.core.designsystem.actions.appbar.AppBarStateI
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetState
import com.awesome.manager.core.designsystem.actions.bottomsheet.BottomSheetStateI
import com.awesome.manager.core.designsystem.actions.navigation.NavigationState
import com.awesome.manager.core.designsystem.actions.navigation.NavigationStateI
import com.awesome.manager.core.designsystem.actions.picker.PickerState
import com.awesome.manager.core.designsystem.actions.picker.PickerStateI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update


abstract class MainState : NavigationStateI by NavigationState(), AppBarStateI by AppBarState(),
    BottomSheetStateI by BottomSheetState(), PickerStateI by PickerState() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
    fun startLoading() = _isLoading.update { true }
    fun endLoading() = _isLoading.update { false }
    suspend fun (suspend () -> Unit).processWitLoading() {
        _isLoading.update { true }
        this.invoke()
        _isLoading.update { false }
    }

}
