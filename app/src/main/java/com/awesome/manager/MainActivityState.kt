package com.awesome.manager

import com.awesome.manager.core.designsystem.text.AmTextManager
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

    val scaffoldActions: ScaffoldActions = ScaffoldActions()

}

class ScaffoldActions(
){

    private val _add:MutableStateFlow<Unit?> = MutableStateFlow(null)
    val add: StateFlow<Unit?> =MutableStateFlow(null)
    fun onAdd(){_add.update {  }}
    fun doneAdd(){_add.update { null }}

    private val _save:MutableStateFlow<Unit?> = MutableStateFlow(null)
    val save: StateFlow<Unit?> =MutableStateFlow(null)
    fun onSave(){_save.update {  }}
    fun doneSave(){_save.update { null }}

    private val _edit:MutableStateFlow<Unit?> = MutableStateFlow(null)
    val edit: StateFlow<Unit?> =MutableStateFlow(null)
    fun onEdit(){_edit.update {  }}
    fun doneEdit(){_edit.update {  }}

}