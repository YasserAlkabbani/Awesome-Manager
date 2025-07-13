package com.awesome.manager.feature.account.editor

import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.feature.account.editor.EditorState.CREATE
import com.awesome.manager.feature.account.editor.EditorState.EDIT

sealed interface AccountEditorState {

    val editorState: EditorState

    data class Init(
        override val editorState: EditorState
    ) : AccountEditorState {
        constructor(accountID: String?) : this(
            when (accountID) {
                null -> CREATE
                else -> EDIT
            }
        )
    }

    data class ValidateInput(
        override val editorState: EditorState,
        val transactionType: AmTransactionType,
        val currency: AmCurrency,
        val accountName: String,
        val imageURL: String,
    ) : AccountEditorState

    data class InvalidateInput(
        override val editorState: EditorState,
        val inValidAccountName: Boolean,
        val invalidImageURL: Boolean,
        val inValidTransactionType: Boolean,
        val invalidCurrency: Boolean,
    ) : AccountEditorState

}

enum class EditorState {
    CREATE,
    EDIT;
}