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
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_BOTTOM
import com.awesome.manager.core.designsystem.UIConstant.SCROLL_CONTENT_PADDING_TOP
import com.awesome.manager.core.designsystem.UIConstant.VERTICAL_SPACE_BETWEEN_ITEMS
import com.awesome.manager.core.designsystem.ui_actions.AppBarAction
import com.awesome.manager.core.ui.AccountCard

@Composable
fun AccountsRoute(
    sendMainAction :(MainActions)->Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
) {

    val accountsState = accountsViewModel.accountsState

    val navigationAction=accountsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendAction(
            sendMainAction=sendMainAction,
            resetNavigation = accountsState::resetNavigationAction
        )
    })

    LaunchedEffect(key1 = Unit, block = {
        AppBarAction.Search({},false).sendAction(sendMainAction)
    })

    AccountsScreen(accountsState)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AccountsScreen(
    accountsState: AccountsState
) {
    val accountsListState = accountsState.accounts.collectAsState().value

    Column(modifier = Modifier.fillMaxSize()) {
        when(accountsListState){
            is DataState.Success -> {
                val accounts=accountsListState.data
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
                                    onClick = { accountsState.navigateToAccountDetails(account.id) },
                                    onAddTransaction = {
                                        accountsState.navigateToCreateTransaction(account.id)
                                    },
                                    onEditTransaction = null
                                )
                            }
                        )
                    }
                )
            }
            DataState.Error , DataState.Loading -> {}
        }
    }
}