package com.awesome.manager.feature.account.accounts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_BOTTOM
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_TOP
import com.awesome.manager.core.designsystem.UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.AccountCard
import com.awesome.manager.core.ui.AmSearch

@Composable
fun AccountsRoute(
    navigateToCreateAccount: () -> Unit,
    navigateToAccountDetails: (AmAccount) -> Unit,
    navigateToCreateTransaction: (AmAccount) -> Unit,
    showProfileBottomSheet: () -> Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
) {

    val accountsState = accountsViewModel.accountsState

    val createAccountNavigation =
        accountsState.createAccountNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = createAccountNavigation, block = {
        if (createAccountNavigation) {
            navigateToCreateAccount()
            accountsState.doneCreateAccountNavigation()
        }
    })

    val accountDetailsNavigation =
        accountsState.accountDetailsNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = accountDetailsNavigation, block = {
        accountDetailsNavigation?.let {
            navigateToAccountDetails(it)
            accountsState.doneAccountDetailsNavigation()
        }
    })

    val createTransactionNavigation =
        accountsState.createTransactionNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = createTransactionNavigation, block = {
        createTransactionNavigation?.let {
            navigateToCreateTransaction(it)
            accountsState.doneCreateTransactionNavigation()
        }
    })

    val profileBottomSheet =
        accountsState.profileBottomSheet.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = profileBottomSheet, block = {
        if (profileBottomSheet) {
            showProfileBottomSheet()
            accountsState.doneProfileBottomSheet()
        }
    })


    AccountsScreen(accountsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsScreen(
    accountsState: AccountsState
) {
    val accounts = accountsState.accounts.collectAsStateWithLifecycle().value
    val accountSearchKey = accountsState.accountSearchKey.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.fillMaxSize()) {
        AmSearch(
            searchKey = accountSearchKey,
            searchLabel = stringResource(R.string.search_for_account),
            onSearchKeyChange = accountsState::updateAccountSearchKey,
            errorMessage = null,
            showProfileBottomSheet = accountsState::showProfileBottomSheet
        )
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = SCROLL_CONTENT_PADDING_TOP.dp,
                bottom = SCROLL_CONTENT_PADDING_BOTTOM.dp
            ),
            verticalArrangement = Arrangement.spacedBy(VERTICAL_SPACE_BETWEEN_ITEMS.dp),
            content = {
                items(
                    items = accounts,
                    contentType = { "ACCOUNTS" },
                    key = { account -> account.id },
                    itemContent = { account ->
                        AccountCard(
                            modifier = Modifier.animateItemPlacement(),
                            title = account.name,
                            imageUrl = account.imageUrl,
                            creditor = account.creditor,
                            debtor = account.debtor,
                            currency = account.currency.currencyCode,
                            loading = account.pending,
                            onClick = { accountsState.startAccountDetailsNavigation(account) },
                            onAddTransaction = {
                                accountsState.startCreateTransactionNavigation(
                                    account
                                )
                            },
                            onEditTransaction = null
                        )
                    }
                )
            }
        )
    }
}