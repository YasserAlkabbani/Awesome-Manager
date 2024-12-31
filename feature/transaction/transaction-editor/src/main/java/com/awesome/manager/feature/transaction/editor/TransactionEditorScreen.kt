package com.awesome.manager.feature.transaction.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.AmUIState
import com.awesome.manager.core.common.asDate
import com.awesome.manager.core.common.asFormattedNumber
import com.awesome.manager.core.ui.actions.main.MainAction
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalIconWithTextButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToString
import com.awesome.manager.core.model.AmAccount
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionEditorRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState

    val mainAction = transactionEditorState.mainAction.collectAsState().value
    LaunchedEffect(key1 = mainAction) {
        mainAction?.sendMainAction(sendMainAction, transactionEditorState::doneMainAction)
    }

    transactionEditorState.transactionEditorUI.collectAsStateWithLifecycle(null)

    TransactionEditorScreen(transactionEditorState = transactionEditorState)
}

@Composable
fun TransactionEditorScreen(
    transactionEditorState: TransactionEditorState,
) {
    val context = LocalContext.current

    val transactionEditorData: AmUIState<TransactionEditorData> =
        transactionEditorState.transactionEditorData.collectAsState().value
    val selectedAccount: AmAccount? =
        transactionEditorState.account.collectAsStateWithLifecycle().value

    val title: String = transactionEditorState.title.collectAsStateWithLifecycle().value
    val subtitle: String = transactionEditorState.subtitle.collectAsStateWithLifecycle().value
    val amount: String = transactionEditorState.amount.collectAsStateWithLifecycle().value
    val transactionAt: Long =
        transactionEditorState.transactionAt.collectAsStateWithLifecycle().value
    val formattedTransactionAt: String = remember(transactionAt) { transactionAt.asDate() }
    val selectedTransactionTypeID =
        transactionEditorState.selectedTransactionTypeID.collectAsStateWithLifecycle().value


    val transactionTypeChipData = remember {
        transactionEditorState.transactionTypes.map {
            val transactionTypeTitle = context.enumToString(it)
            ChipData(id = it.id, title = transactionTypeTitle)
        }
    }

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
        targetState = transactionEditorData,
        label = "TRANSACTION_EDITOR"
    ) {
        when (it) {
            is AmUIState.Error -> Unit
            is AmUIState.Loading -> Unit
            is AmUIState.Success -> {
                Column(
                    modifier = Modifier
                        .padding(horizontal = 6.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    selectedAccount?.let { account ->
                        val balanceDetails = account.balanceDetails
                        AccountCard(
                            modifier = Modifier,
                            title = account.name, imageUrl = account.imageUrl,
                            loading = account.pending, withDetails = true,
                            onClick = {},
                            onAddTransaction = null, onEditTransaction = null,
                            income = balanceDetails.formattedIncome,
                            expenses = balanceDetails.formattedExpenses,
                            netIncomeAbs = balanceDetails.formattedNetIncome,
                            debtor = balanceDetails.formattedDebtor,
                            creditor = balanceDetails.formattedCreditor,
                            netDebtorAbs = balanceDetails.formattedNetDebtor,
                            currencySymbol = balanceDetails.currency.currencySymbol,
                            isPositiveIncome = balanceDetails.isPositiveIncome,
                            isPositiveDebtor = balanceDetails.isPositiveDebtor,
                        )
                    }

                    AmTextField(
                        modifier = Modifier,
                        hint = "Title",
                        icon = AmIcons.Title,
                        label = "Transaction Title",
                        onTextChange = transactionEditorState::updateTitle,
                        keyboardActions = KeyboardActions(),
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        text = title
                    )
                    AmTextField(
                        hint = "5000.0", icon = AmIcons.Money, label = "Amount",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done, keyboardType = KeyboardType.Number,
                        ),
                        onTextChange = transactionEditorState::updateAmount,
                        text = amount,
                        formatText = { asFormattedNumber() }
                    )
                    AmTextField(
                        hint = "Description",
                        label = "Transaction Description",
                        icon = AmIcons.SubTitle,
                        singleLine = false,
                        onTextChange = transactionEditorState::updateSubtitle,
                        text = subtitle,
                    )

                    AmFilledTonalIconWithTextButton(
                        text = formattedTransactionAt,
                        amIconsType = AmIcons.Date,
                        positive = null,
                        onClick = {
                            transactionEditorState.showPickDateBottomSheet(
                                initTime = transactionAt,
                                setDate = transactionEditorState::updateTransactionAt
                            )
                        }
                    )

                    AmChipsContainer(
                        title = stringResource(R.string.transaction_type),
                        chipDataList = transactionTypeChipData,
                        onSelect = { transactionEditorState.updateTransactionType(it.id) },
                        selectedItem = selectedTransactionTypeID,
                        content = null
                    )
                }
            }
        }
    }

}

