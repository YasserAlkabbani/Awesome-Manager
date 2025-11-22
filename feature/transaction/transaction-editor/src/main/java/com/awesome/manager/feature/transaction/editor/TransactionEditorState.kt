package com.awesome.manager.feature.transaction.editor


sealed interface TransactionEditorState {

    data object Init : TransactionEditorState

    data class Validated(
        val title: String,
        val subtitle: String,
        val amount: String,
        val accountID: String,
        val transactionTypeID: String,
        val transactionAt: Long,
    ) : TransactionEditorState

    data class InvalidatedInput(
        val isValidTitle: Boolean,
        val isValidAccountID: Boolean,
        val isValidAmount: Boolean,
        val isValidTransactionType: Boolean
    ) : TransactionEditorState

}

internal sealed interface TransactionEditorEvents {
    data object Idle : TransactionEditorEvents
    data object PopupNavigation : TransactionEditorEvents
    data class TransactionDetailsNavigation(val transactionID: String) : TransactionEditorEvents
}