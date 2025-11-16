package com.awesome.manager.feature.transaction.details

import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.model.AmTransaction


sealed interface TransactionDetailsState

sealed interface TransactionDetailsActions {
    data object Idle : TransactionDetailsActions
    data class TransactionEditorNavigation(val transaction: AmTransaction):TransactionDetailsActions
    data class AccountDetailsNavigation(val account: AmAccount):TransactionDetailsActions
}