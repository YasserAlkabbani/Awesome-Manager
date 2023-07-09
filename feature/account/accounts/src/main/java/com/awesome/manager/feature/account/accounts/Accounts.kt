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
import com.awesome.manager.core.ui.AccountCard
import timber.log.Timber

@Composable
fun AccountsRoute(
    navigateToAccountDetails:(String)->Unit,
    navigateToCreateAccount:()->Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
){

    val accountsState=accountsViewModel.accountsState

    val createAccountNavigation=accountsState.createAccountNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = createAccountNavigation, block = {
        createAccountNavigation?.let {
            navigateToCreateAccount()
            accountsState.doneCreateAccountNavigation()
        }
    })

    val accountDetailsNavigation=accountsState.accountDetailsNavigation.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = accountDetailsNavigation, block = {
        Timber.d("TEST_ACCOUNT_NAVIGATION ACCOUNT_DETAILS_SCREEN $accountDetailsNavigation")
        accountDetailsNavigation?.let {
            navigateToAccountDetails(it)
            accountsState.doneAccountDetailsNavigation()
        }
    })

    AccountsScreen(accountsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsScreen(
    accountsState: AccountsState
){
    val accounts=accountsState.accounts.collectAsState().value
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp) ,
        verticalArrangement = Arrangement.spacedBy(0.5.dp),
        content = {
            items(
                items = accounts,
                contentType = {"ACCOUNTS"},
                key = {account->account.id},
                itemContent = {account->
                    AccountCard(
                        modifier = Modifier.animateItemPlacement(),
                        title = account.name,
                        imageUrl = account.imageUrl,
                        creditor = account.creditor,
                        debtor = account.debtor,
                        currency = account.currency.currencySymbol,
                        loading = account.pending,
                        onClick = {}
                    )
                }
            )
        }
    )
}