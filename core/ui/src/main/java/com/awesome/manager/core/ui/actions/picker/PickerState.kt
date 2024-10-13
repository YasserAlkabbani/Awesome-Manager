package com.awesome.manager.core.ui.actions.picker

import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.ui.actions.main.PickerAction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

interface PickerState {

    fun PickerAction.applyAction()

}