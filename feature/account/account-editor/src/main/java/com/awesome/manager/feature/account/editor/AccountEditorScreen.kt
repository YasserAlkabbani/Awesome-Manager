package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.ui_actions.AppBarAction
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.component.AmChip
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons

@Composable
fun AccountEditorRoute(
    sendMainAction :(MainActions)->Unit,
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel(),
) {

    val accountEditorState = accountEditorViewModel.accountEditorState

    val navigationAction = accountEditorState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendAction(
            sendMainAction = sendMainAction,
            resetNavigation =accountEditorState::resetNavigationAction
            )
    })

    val createAccountText = stringResource(id = R.string.create_account)
    val editAccountText = stringResource(R.string.edit_account)
    val account = null
    LaunchedEffect(key1 = account, block = {
        val title = when (account) {
            null -> createAccountText
            else -> editAccountText
        }
        AppBarAction.Edit(
            title = title,
            onCancel = accountEditorState::navigatePopBack,
            onSave = accountEditorState.onSave
        ).sendAction(sendMainAction)
    })

    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState: AccountEditorState) {

    val accountName: String = accountEditorState.name.collectAsState().value
    val accountImage: String = accountEditorState.imageUrl.collectAsState().value
    val currencies = accountEditorState.currencies.collectAsState().value
    val selectedCurrency = accountEditorState.selectedCurrency.collectAsState().value
    val transactionTypes = accountEditorState.transactionTypes.collectAsState().value
    val defaultTransactionType =
        accountEditorState.defaultTransactionType.collectAsState().value

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ) {

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AmImage(modifier = Modifier.size(70.dp), imageUrl = accountImage)
            Spacer(modifier = Modifier.width(12.dp))
            AmTextField(
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                label = "Name", icon = AmIcons.Title, hint = "Account Name",
                error = null, onTextChange = accountEditorState::updateName
            )
        }
        Spacer(modifier = Modifier.height(6.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            AmText(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Currency",
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(items = currencies, key = { it.id }, contentType = { "CURRENCY" }) {
                    AmChip(
                        selected = selectedCurrency?.id == it.id,
                        label = it.currencyName,
                        onClick = { accountEditorState.updateCurrency(it.id) })
                }
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp)
        ) {
            AmText(
                modifier = Modifier.padding(horizontal = 8.dp),
                text = "Default Transaction Type",
                style = MaterialTheme.typography.titleMedium
            )
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                contentPadding = PaddingValues(horizontal = 8.dp)
            ) {
                items(
                    items = transactionTypes,
                    key = { it.id },
                    contentType = { "TRANSACTION_TYPE" }) {
                    AmChip(
                        selected = defaultTransactionType?.id == it.id,
                        label = it.title,
                        onClick = { accountEditorState.updateDefaultTransactionType(it.id) })
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))
    }
}