package com.awesome.manager.feature.account.accounts

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AppBarData
import com.awesome.manager.core.ui.AccountCard
import timber.log.Timber

@Composable
fun AccountsRoute(
    navigateToCreateAccount: () -> Unit,
    navigateToAccountDetails: (String) -> Unit,
    navigateToCreateTransaction: (String) -> Unit,
    updateAppBarState: (appBarData: AppBarData?) -> Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
) {

    val accountsState = accountsViewModel.accountsState

    val createAccountNavigation =
        accountsState.createAccountNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = createAccountNavigation, block = {
        createAccountNavigation?.let {
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

    LaunchedEffect(key1 = Unit, block = {
        updateAppBarState(null)
    })

    AccountsScreen(accountsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsScreen(
    accountsState: AccountsState
) {
    val accounts = accountsState.accounts.collectAsStateWithLifecycle().value
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
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
                        currency = account.currency.currencySymbol,
                        loading = account.pending,
                        onClick = { accountsState.startAccountDetailsNavigation(account.id) },
                        onAddTransaction = { accountsState.startCreateTransactionNavigation(account.id) },
                        onEditTransaction = null
                    )
                }
            )
        }
    )
}