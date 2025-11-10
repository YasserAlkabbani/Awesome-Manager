package com.awesome.manager.feature.account.editor

sealed interface AccountEditorState {

    data object Init : AccountEditorState

    data class ValidateInput(
        val accountName: String,
        val imageURL: String?,
        val defaultTransactionTypeID: String,
        val currencyID: String
    ) : AccountEditorState

    data class InvalidateInput(
        val invalidAccountName: Boolean,
        val invalidTransactionType: Boolean,
        val invalidCurrency: Boolean,
    ) : AccountEditorState

}

sealed interface AccountEditorEvents{
    data object Idle:AccountEditorEvents
    data object Popup:AccountEditorEvents
}