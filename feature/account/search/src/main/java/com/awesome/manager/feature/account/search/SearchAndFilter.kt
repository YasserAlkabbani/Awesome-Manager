package com.awesome.manager.feature.account.search

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.rememberAmSheetState
import com.awesome.manager.core.model.AmAccount


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsRoute(
    onSelectAccount:(AmAccount)->Unit,
    accountsViewModel: AccountsViewModel = hiltViewModel()
){

    val accountsState=accountsViewModel.accountsState


    val createAccountAmSheetState= rememberAmSheetState()
    val createAccountAction=accountsState.createAccountAction.collectAsState().value
    LaunchedEffect(key1 = createAccountAction, block = {
        createAccountAction?.let {
            accountsState.doneCreateAccountAction()
            createAccountAmSheetState.open()
        }
    })

    AccountsScreen(accountsState)
}


@Composable
fun AccountsScreen(
    accountsState: AccountsState
){
    val accounts=accountsState.accounts.collectAsState().value
    LazyColumn(
        contentPadding = PaddingValues(8.dp) ,
        content = {
            items(
                items = accounts,
                contentType = {"ACCOUNTS"},
                key = {account->account.id},
                itemContent = {
                    AmText(text = it.name)
                }
            )
        }
    )
}