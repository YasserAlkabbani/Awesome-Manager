package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.awesome.manager.core.common.extentions.limitName
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.appbar.sendMainAction
import com.awesome.manager.core.designsystem.actions.bottomsheet.sendMainAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.sendMainAction
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmSpacerMediumHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
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
        navigationAction.sendMainAction(sendMainAction, accountEditorState::doneNavigationAction)
    })

    val appBarAction = accountEditorState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, accountEditorState::doneAppBarAction)
    })

    val bottomSheetAction = accountEditorState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, accountEditorState::doneBottomSheetAction)
    })

    val accountEditorData = accountEditorState.accountEditorData.collectAsState().value


    val createAccountText = stringResource(id = R.string.create_account)
    val updateAccountText = stringResource(id = R.string.update_account)
    LaunchedEffect(key1 = accountEditorData) {
        if (accountEditorData is DataState.Success) {
            when (val accountEditor = accountEditorData.data) {
                is AccountEditorData.AccountEditorCreate -> accountEditorState.setForEditAccountScreen(
                    onClickCancel = accountEditorState::navigatePopBack,
                    onSaveButton = accountEditorState.onSave,
                    saveButtonText = createAccountText,
                )

                is AccountEditorData.AccountEditorUpdate -> accountEditorState.setForEditAccountScreen(
                    onClickCancel = accountEditorState::navigatePopBack,
                    onSaveButton = accountEditorState.onSave,
                    saveButtonText = "$updateAccountText ${accountEditor.name.limitName()}",
                )
            }
        }
    }


    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState: AccountEditorStateMain) {

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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
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
            AmSpacerMediumHeight()
            AmChipsContainer(
                title = "Currency",
                chipDataList = currencyChipData,
                selectedItem = account.currency?.id,
                onSelect = { accountEditorState.updateCurrency(it.data) },
                content = null
            )
            AmSpacerSmallHeight()
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