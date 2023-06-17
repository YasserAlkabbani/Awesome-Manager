package com.awesome.manager.feature.account.editor

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.awesome.manager.feature.account.editor.navigation.AccountEditorArg
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountEditorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
):ViewModel() {

    val accountEditorState:AccountEditorState=AccountEditorState()

    init {
        AccountEditorArg(savedStateHandle).let {
            accountEditorState.updateAccountId(it.accountId)
        }
    }

}