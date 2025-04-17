package com.awesome.manager.ui

/*
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
//    val searchKey: String = mainActivityState.searchKey.collectAsStateWithLifecycle().value
//    val accountsLazyPagingItems: LazyPagingItems<AmAccount> =
//        mainActivityState.accountsSearchPagingData.collectAsLazyPagingItems()
//    BottomSheetSearch(
//        searchHint = "Search Key",
//        searchLabel = "Search for an account",
//        searchKey = searchKey,
//        onUpdateSearchKey = {
//            Timber.d("TEST_SEARCH CHANGE_SEARCH_KEY $it")
//            mainActivityState.updateSearchKey(it)
//        },
//        onSearchDone = hideKeyboard,
//        searchTypes = listOf(SearchType.ACCOUNT),
//        selectedSearchType = SearchType.ACCOUNT,
//        onUpdateSearchType = {},
//        items = {
//            items(
//                count = accountsLazyPagingItems.itemCount,
//                contentType = { LAZY_ITEM_ACCOUNT },
//                key = accountsLazyPagingItems.itemKey { account -> account.id },
//                itemContent = { index ->
//                    accountsLazyPagingItems[index]?.let { account ->
//                        val balanceDetails = account.balanceDetails
//                        AccountCard(
//                            modifier = Modifier.animateItem(),
//                            title = account.name,
//                            imageUrl = account.imageUrl,
//                            loading = account.pending,
//                            withDetails = false,
//                            onClick = {
//                                onSelectAccount(account.id)
//                                dismiss()
//                            },
//                            incomeExpenses = BalanceDetails.IncomeExpenses(
//                                income = balanceDetails.formattedIncome,
//                                expenses = balanceDetails.formattedExpenses,
//                                netIncomeAbs = balanceDetails.formattedNetIncome,
//                                isPositiveIncome = balanceDetails.isPositiveIncome,
//                            ),
//                            creditorDebtor = BalanceDetails.CreditorDebtor(
//                                debtor = balanceDetails.formattedDebtor,
//                                creditor = balanceDetails.formattedCreditor,
//                                netDebtorAbs = balanceDetails.formattedNetDebtor,
//                                isPositiveDebtor = balanceDetails.isPositiveDebtor
//                            ),
//                            currencySymbol = balanceDetails.currency.currencySymbol,
//                        )
//                    }
//                }
//            )
//        }
//    )

}*/
