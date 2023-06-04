package com.awesome.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityState(
    val isLogin:StateFlow<Boolean>
) {

    private val _onClickFab:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val clickFab:StateFlow<Unit?> =_onClickFab.asStateFlow()
    fun onClickFab(){_onClickFab.update {  }}
    fun doneClickFab(){_onClickFab.update { null }}

}