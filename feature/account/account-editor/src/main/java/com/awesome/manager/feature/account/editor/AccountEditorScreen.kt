package com.awesome.manager.feature.account.editor

import android.content.Context
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.enums.EditorInputType.*
import com.awesome.manager.core.common.extentions.limitName
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.actions.appbar.AppBarButton
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmSpacerMediumHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToString
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.getChipData

@Composable
fun AccountEditorRoute(
    sendMainAction: (MainAction) -> Unit,
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel(),
) {

    val accountEditorState = accountEditorViewModel.accountEditorState

    val mainAction = accountEditorState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, accountEditorState::doneMainAction)
    }

    val accountEditorData = accountEditorState.accountEditorData.collectAsState().value
    val context: Context = LocalContext.current
    LaunchedEffect(key1 = accountEditorData) {
        if (accountEditorData is DataState.Success) {
            val accountEditor = accountEditorData.data
            val isValidInput = accountEditor.validateAccountData != null
            val errorMessage =
                if (isValidInput) null else context.getString(R.string.invalidate_input)
            val buttonText = when (accountEditor.editorInputType) {
                Create -> context.getString(R.string.create_account)
                Edit -> "${context.getString(R.string.update_account)} ${accountEditor.name.limitName()}"
            }
            accountEditorState.setForEditAccountScreen(
                cancelButton = true,
                saveButton = AppBarButton(
                    text = buttonText, click = accountEditorState.onSave,
                    errorMessage = errorMessage
                ),
            )
        }
    }


    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState: AccountEditorState) {

    val context = LocalContext.current
    val accountData = accountEditorState.accountEditorData.collectAsState().value
    val currencies = accountEditorState.currencies.collectAsState().value
    val currencyChipData = remember(currencies) {
        currencies.map {
            getChipData(id = it.id, title = it.currencyName, data = it)
        }
    }
    val transactionTypeChipData = remember {
        accountEditorState.transactionTypes.map {
            val title = context.enumToString(it)
            getChipData(id = it.name, title = title, data = it)
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
                    label = "Account", icon = AmIcons.Title, hint = "Account Name",
                    onTextChange = accountEditorState::updateName
                )
            }
            if (account.allowToUpdateCurrency) {
                AmSpacerMediumHeight()
                AmChipsContainer(
                    title = "Currency",
                    chipDataList = currencyChipData,
                    selectedItem = account.currency?.id,
                    onSelect = { accountEditorState.updateCurrency(it.data) },
                    content = null
                )
            }
            AmSpacerSmallHeight()
            AmChipsContainer(
                title = stringResource(R.string.default_transaction_type),
                chipDataList = transactionTypeChipData,
                selectedItem = account.defaultTransactionType?.name,
                onSelect = { accountEditorState.updateDefaultTransactionType(it.data) },
                content = null
            )

        }

    }

}