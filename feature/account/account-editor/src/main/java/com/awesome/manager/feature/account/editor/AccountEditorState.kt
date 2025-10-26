package com.awesome.manager.feature.account.editor

internal sealed interface AccountEditorState {

    data object Init : AccountEditorState

    data object ValidateInput : AccountEditorState

    data class InvalidateInput(
        val invalidAccountName: Boolean,
        val invalidTransactionType: Boolean,
        val invalidCurrency: Boolean,
    ) : AccountEditorState

}

internal sealed interface AccountEditorNavigation{
    data object Popup:AccountEditorNavigation
}