package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.ui.actions.main.MainAction
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

    accountEditorState.accountEditorUIState.collectAsStateWithLifecycle(null)

    AccountEditorScreen(accountEditorState)

}

@Composable
fun AccountEditorScreen(accountEditorState: AccountEditorState) {

    val context = LocalContext.current
    val accountData = accountEditorState.accountEditorData.collectAsState().value
    val currencies = accountEditorState.currencies.collectAsState().value

    val accountName = accountEditorState.accountName.collectAsStateWithLifecycle().value
    val accountImageUrl = accountEditorState.accountImageUrl.collectAsStateWithLifecycle().value
    val selectedCurrencyID = accountEditorState.selectedCurrency.collectAsStateWithLifecycle().value
    val selectedTransactionType =
        accountEditorState.selectedTransactionType.collectAsStateWithLifecycle().value

    val currencyChipData = remember(currencies) {
        (currencies as? AmUIState.Success)?.data.orEmpty().map {
            getChipData(id = it.id, title = it.currencyName)
        }
    }
    val transactionTypeChipData = remember {
        accountEditorState.transactionTypes.map {
            val title = context.enumToString(it)
            getChipData(id = it.name, title = title)
        }
    }

    when (accountData) {
        is AmUIState.Error -> Unit
        is AmUIState.Loading -> Unit
        is AmUIState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AmPadding.MEDIUM.value),
                verticalArrangement = Arrangement.spacedBy(AmPadding.MEDIUM.value)
            ) {
                Row(
                    modifier = Modifier,
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(AmPadding.MEDIUM.value)
                ) {
                    AmImage(
                        modifier = Modifier.size(AmSize.XX_LARGE.value),
                        imageUrl = accountImageUrl
                    )
                    AmTextField(
                        modifier = Modifier,
                        singleLine = true, text = accountName,
                        label = "Account", icon = AmIcons.Title, hint = "Account Name",
                        onTextChange = accountEditorState::onUpdateAccountName
                    )
                }
                AmChipsContainer(
                    title = "Currency",
                    chipDataList = currencyChipData,
                    selectedItem = selectedCurrencyID,
                    onSelect = { accountEditorState.onUpdateCurrency(it.id) },
                    content = null
                )
                AmChipsContainer(
                    title = stringResource(R.string.default_transaction_type),
                    chipDataList = transactionTypeChipData,
                    selectedItem = selectedTransactionType,
                    onSelect = { accountEditorState.onUpdateTransactionType(it.id) },
                    content = null
                )

            }
        }
    }

}