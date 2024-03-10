package com.awesome.manager.core.ui.bottom_sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AccountCard

@Composable
fun SearchForAccount() {
    val focusRequester: FocusRequester = FocusRequester()
    AmTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .onGloballyPositioned {
                focusRequester.requestFocus()
            },
        hint = "Search For Account", icon = AmIcons.Search, label = "Something..",
         error = null,
        onTextChange = {/*transactionEditorState::updateAccountSearchKey*/ }
    )
    Spacer(modifier = Modifier.height(8.dp))
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 16.dp),
        verticalArrangement = Arrangement.spacedBy(1.dp),
        content = {
//            items(
//                items = accountSearchResult,
//                contentType = { "ACCOUNTS" },
//                key = { account -> account.id },
//                itemContent = { account ->
//                    AccountCard(
//                        modifier = Modifier.animateItemPlacement(),
//                        title = account.name,
//                        imageUrl = account.imageUrl,
//                        creditor = account.creditor,
//                        debtor = account.debtor,
//                        currency = account.currency.currencySymbol,
//                        loading = account.pending,
//                        onClick = { transactionEditorState.selectAccount(account) },
//                        onAddTransaction = null,
//                        onEditTransaction = null
//                    )
//                }
//            )
        }
    )
}