package com.awesome.manager.feature.account.editor

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AccountEditorState {

    private val _accountId:MutableStateFlow<String?> =MutableStateFlow("")
    val accountId:StateFlow<String?> =_accountId
    fun updateAccountId(id:String?){_accountId.update { id }}

}