package com.awesome.manager.feature.transaction.editor


internal sealed interface TransactionEditorState {

    data object Init : TransactionEditorState

    data object Validated : TransactionEditorState

    data class InvalidatedInput(
        val isValidTitle: Boolean,
        val isValidAccountID: Boolean
    ) : TransactionEditorState

}

internal sealed interface TransactionEditorNavigation{
    data object Popup:TransactionEditorNavigation
    data class TransactionDetails(val transactionID: String):TransactionEditorNavigation
}