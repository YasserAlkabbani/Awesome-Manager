package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.ui_actions.main.MainAction
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.getChipData

@Composable
fun AccountEditorRoute(
    sendMainAction: (MainAction) -> Unit,
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel(),
) {

    val accountEditorState = accountEditorViewModel.accountEditorState

    val navigationAction = accountEditorState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, accountEditorState::resetNavigationAction)
    })

    val appBarAction = accountEditorState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, accountEditorState::resetAppBar)
    })

    val bottomSheetAction = accountEditorState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, accountEditorState::idleBottomSheet)
    })

    val accountEditorData = accountEditorState.accountEditorData.collectAsState().value
    if (accountEditorData is DataState.Success) {
        when (accountEditorData.data) {
            is AccountEditorData.AccountEditorCreate -> accountEditorState.showCreateAppBar(
                title = stringResource(id = R.string.create_account),
                onCancel = accountEditorState::navigatePopBack,
                onSave = accountEditorState.onSave
            )

            is AccountEditorData.AccountEditorUpdate -> accountEditorState.showEditAppBar(
                title = stringResource(
                    R.string.update_account,
                    accountEditorData.data.name
                ),
                onCancel = accountEditorState::navigatePopBack,
                onSave = accountEditorState.onSave
            )
        }
    }


    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState: AccountEditorState) {

    val accountData = accountEditorState.accountEditorData.collectAsState().value
    val currencies = accountEditorState.currencies.collectAsState().value
    val currencyChipData = remember(currencies) {
        currencies.map {
            getChipData(id = it.id, title = it.currencyName, data = it)
        }
    }
    val transactionTypes = accountEditorState.transactionTypes
    val transactionTypeChipData = remember(transactionTypes) {
        transactionTypes.map {
            getChipData(id = it.name, title = it.name, data = it)
        }
    }

    if (accountData is DataState.Success) {
        val account = accountData.data
        Column(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmImage(modifier = Modifier.size(70.dp), imageUrl = account.imageUrl)
                Spacer(modifier = Modifier.width(12.dp))
                AmTextField(
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true, initTextValue = account.name,
                    label = "Name", icon = AmIcons.Title, hint = "Account Name",
                    error = null, onTextChange = accountEditorState::updateName
                )
            }
            Spacer(modifier = Modifier.height(6.dp))

            AmChipsContainer(
                title = "Currency",
                chipDataList = currencyChipData,
                selectedItem = account.currency?.id,
                onSelect = { accountEditorState.updateCurrency(it.data) },
                content = null
            )
            AmChipsContainer(
                title = "Default Transaction Type",
                chipDataList = transactionTypeChipData,
                selectedItem = account.defaultTransactionType?.name,
                onSelect = { accountEditorState.updateDefaultTransactionType(it.data) },
                content = null
            )

        }

    }

}