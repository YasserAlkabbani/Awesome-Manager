package com.awesome.manager

import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.designsystem.icon.AmIconsType
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MainActivityState(
    val isLogin: StateFlow<Boolean>
) {

    private val _selectedAccount: MutableStateFlow<AmAccount?> = MutableStateFlow(null)
    val selectedAccount: StateFlow<AmAccount?> = _selectedAccount
    fun onSelectedAccount(amAccount: AmAccount?) {
        _selectedAccount.update { amAccount }
    }

    private val _selectedTransaction: MutableStateFlow<AmTransaction?> = MutableStateFlow(null)
    val selectedTransaction: StateFlow<AmTransaction?> = _selectedTransaction
    fun onSelectedTransaction(amTransaction: AmTransaction?) {
        _selectedTransaction.update { amTransaction }
    }

    private val _navigationBack: MutableStateFlow<Unit?> = MutableStateFlow(null)
    val navigationBack: StateFlow<Unit?> = _navigationBack
    fun onNavigationBack() {
        _navigationBack.update { }
    }

    fun doneNavigationBack() {
        _navigationBack.update { null }
    }

    private val _appBarState: MutableStateFlow<AppBarData?> = MutableStateFlow(null)
    val appBarState: StateFlow<AppBarData?> = _appBarState
    fun updateAppBarState(appBarData: AppBarData?) {
        _appBarState.update { appBarData }
    }

}