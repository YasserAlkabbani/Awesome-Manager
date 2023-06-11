package com.awesome.manager

import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MainActivityState(
    val isLogin:StateFlow<Boolean>
) {

    private val _clickBack:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val clickBack:StateFlow<Unit?> =_clickBack
    fun onClickBack(){_clickBack.update {  }}
    fun doneClickBack(){_clickBack.update { null }}

    private val _clickAdd:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val clickAdd:StateFlow<Unit?> =_clickAdd.asStateFlow()
    fun onClickAdd(){_clickAdd.update {  }}
    fun doneClickAdd(){_clickAdd.update { null }}

    private val _clickEdit:MutableStateFlow<Unit?> =MutableStateFlow(null)
    val clickEdit:StateFlow<Unit?> =_clickEdit
    fun onClickEdit(){_clickEdit.update {  }}
    fun doneClickEdit(){_clickEdit.update { null }}

    private val _selectedAccount:MutableStateFlow<AmAccount?> =MutableStateFlow(null)
    val selectedAccount:StateFlow<AmAccount?> =_selectedAccount
    fun onSelectedAccount(amAccount:AmAccount?){_selectedAccount.update{amAccount}}

    private val _selectedTransaction:MutableStateFlow<AmTransaction?> =MutableStateFlow(null)
    val selectedTransaction:StateFlow<AmTransaction?> =_selectedTransaction
    fun onSelectedTransaction(amTransaction: AmTransaction?){_selectedTransaction.update { amTransaction }}

}