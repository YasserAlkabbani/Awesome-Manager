package com.awesome.manager.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.MainActivityState
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.actions.main.BottomSheetAction
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetDatePicker
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetDateRangePicker
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetProfile
import com.awesome.manager.core.ui.bottom_sheets.BottomSheetSearch
import com.awesome.manager.core.ui.bottom_sheets.SearchType
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAccountCreated
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetAuthError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetConnectionError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetCustomError
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetPasswordRestored
import com.awesome.manager.core.ui.bottom_sheets.auth.BottomSheetUnknownError
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.card.BalanceDetails
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_ACCOUNT
import kotlinx.coroutines.launch
import timber.log.Timber

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetAction.AmBottomSheet(
    mainActivityState: MainActivityState,
    resetBottomSheet: () -> Unit,
) {
    val showBottomSheet = remember { mutableStateOf(true) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()

    Timber.d("TEST_MAIN_ACTION BOTTOM_SHEET SHOW ${showBottomSheet.value} ${sheetState.currentValue}")
    if (showBottomSheet.value) {
        ModalBottomSheet(
            modifier = Modifier,
            onDismissRequest = {
                showBottomSheet.value = false
                resetBottomSheet()
            },
            sheetState = sheetState,
            properties = ModalBottomSheetProperties(),
            content = {
                val localKeyboardController = LocalSoftwareKeyboardController.current
                Column(
                    modifier = Modifier.padding(6.dp),
                    content = {
                        Content(
                            mainActivityState = mainActivityState,
                            hideKeyboard = { localKeyboardController?.hide() },
                            dismiss = {
                                scope
                                    .launch { sheetState.hide() }
                                    .invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            showBottomSheet.value = false
                                            resetBottomSheet()
                                        }
                                    }
                            }
                        )
                    }
                )
            },
        )
    }
}

@Composable
private fun BottomSheetAction.Content(
    mainActivityState: MainActivityState,
    dismiss: () -> Unit,
    hideKeyboard: () -> Unit
): Unit =
    when (this) {
        is BottomSheetAction.AccountCreated -> BottomSheetAccountCreated()
        is BottomSheetAction.AuthError -> BottomSheetAuthError()
        is BottomSheetAction.ConnectionError -> BottomSheetConnectionError()
        is BottomSheetAction.CustomError -> BottomSheetCustomError()
        is BottomSheetAction.PasswordRested -> BottomSheetPasswordRestored()
        is BottomSheetAction.Profile -> BottomSheetProfile()
        is BottomSheetAction.UnknownError -> BottomSheetUnknownError()
        is BottomSheetAction.PickDate -> Content(dismiss = dismiss)
        is BottomSheetAction.PickRangeDate -> BottomSheetDateRangePicker()
        is BottomSheetAction.SearchForAccount -> Content(
            mainActivityState = mainActivityState,
            dismiss = dismiss,
            hideKeyboard = hideKeyboard,
        )
    }


@Composable
private fun BottomSheetAction.PickDate.Content(dismiss: () -> Unit) {
    BottomSheetDatePicker(
        initTime = initTime,
        dismiss = dismiss,
        setDate = setDate
    )
}

@Composable
private fun BottomSheetAction.SearchForAccount.Content(
    mainActivityState: MainActivityState,
    dismiss: () -> Unit,
    hideKeyboard: () -> Unit
) {
    val searchKey: String = mainActivityState.searchKey.collectAsStateWithLifecycle().value
    val accountsLazyPagingItems: LazyPagingItems<AmAccount> =
        mainActivityState.accountsSearchPagingData.collectAsLazyPagingItems()
    BottomSheetSearch(
        searchHint = "Search Key",
        searchLabel = "Search for an account",
        searchKey = searchKey,
        onUpdateSearchKey = {
            Timber.d("TEST_SEARCH CHANGE_SEARCH_KEY $it")
            mainActivityState.updateSearchKey(it)
        },
        onSearchDone = hideKeyboard,
        searchTypes = listOf(SearchType.ACCOUNT),
        selectedSearchType = SearchType.ACCOUNT,
        onUpdateSearchType = {},
        items = {
            items(
                count = accountsLazyPagingItems.itemCount,
                contentType = { LAZY_ITEM_ACCOUNT },
                key = accountsLazyPagingItems.itemKey { account -> account.id },
                itemContent = { index ->
                    accountsLazyPagingItems[index]?.let { account ->
                        val balanceDetails = account.balanceDetails
                        AccountCard(
                            modifier = Modifier.animateItem(),
                            title = account.name,
                            imageUrl = account.imageUrl,
                            loading = account.pending,
                            withDetails = false,
                            onClick = {
                                onSelectAccount(account.id)
                                dismiss()
                            },
                            incomeExpenses = BalanceDetails.IncomeExpenses(
                                income = balanceDetails.formattedIncome,
                                expenses = balanceDetails.formattedExpenses,
                                netIncomeAbs = balanceDetails.formattedNetIncome,
                                isPositiveIncome = balanceDetails.isPositiveIncome,
                            ),
                            creditorDebtor = BalanceDetails.CreditorDebtor(
                                debtor = balanceDetails.formattedDebtor,
                                creditor = balanceDetails.formattedCreditor,
                                netDebtorAbs = balanceDetails.formattedNetDebtor,
                                isPositiveDebtor = balanceDetails.isPositiveDebtor
                            ),
                            currencySymbol = balanceDetails.currency.currencySymbol,
                        )
                    }
                }
            )
        }
    )

}