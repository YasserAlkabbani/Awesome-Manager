package com.awesome.manager.feature.transaction.editor

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.input.TextFieldLineLimits
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
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
import com.awesome.manager.core.common.AmState
import com.awesome.manager.core.common.asDate
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToRes
import com.awesome.manager.core.model.AmAccountWithBalance
import com.awesome.manager.core.ui.card.AccountCard
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData
import com.awesome.manager.core.ui.card.AccountCardWithDetails
import com.awesome.manager.core.ui.card.CardBalanceDetails

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TransactionEditorScreen(
//    sendMainAction: (MainAction) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val context = LocalContext.current
    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState

//    val mainAction = transactionEditorState.mainAction.collectAsState().value
//    LaunchedEffect(key1 = mainAction) {
//        mainAction?.sendMainAction(sendMainAction, transactionEditorState::doneMainAction)
//    }

    transactionEditorState.transactionEditorUI.collectAsStateWithLifecycle(null)

    TransactionEditorScreen(transactionEditorState = transactionEditorState)
}

@Composable
internal fun TransactionEditorScreen(
    transactionEditorState: TransactionEditorState,
) {
    val context = LocalContext.current

    val transactionEditorData: AmState<TransactionEditorData> =
        transactionEditorState.transactionEditorData.collectAsState().value
    val selectedAccount: AmAccountWithBalance? =
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
            ChipData(id = it.id, titleRes = it.enumToRes(), title = it.name)
        }
    }

    AnimatedContent(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.TopCenter,
        targetState = transactionEditorData,
        label = "TRANSACTION_EDITOR"
    ) {
        when (it) {
            is AmState.Error -> Unit
            is AmState.Loading -> Unit
            is AmState.Success -> {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    selectedAccount?.let { accountWithBalance ->
                        val account = accountWithBalance.account
                        val balanceDetails = accountWithBalance.balanceDetails
                        AccountCardWithDetails(
                            modifier = Modifier,
                            title = account.name, imageUrl = account.imageUrl,
                            loading = account.pending,
                            creditorDebtor = CardBalanceDetails.CreditorDebtor(
                                debtor = balanceDetails.formattedDebtor,
                                creditor = balanceDetails.formattedCreditor,
                                netDebtorAbs = balanceDetails.formattedNetDebtor,
                                isPositiveDebtor = balanceDetails.isPositiveDebtor,
                            ),
                            incomeExpenses = CardBalanceDetails.IncomeExpenses(
                                income = balanceDetails.formattedIncome,
                                expenses = balanceDetails.formattedExpenses,
                                netIncomeAbs = balanceDetails.formattedNetIncome,
                                isPositiveIncome = balanceDetails.isPositiveIncome,
                            ),
                            currencySymbol = balanceDetails.currency.currencySymbol,
                            lastTransactionAt = "12-11-2025 15:08"
                        )
                    }

                    AmTextField(
                        modifier = Modifier,
                        placeHolder = "Title",
                        icon = AmIcons.Title,
                        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                        textFieldState = TextFieldState(),
                        isValidateInput = true
                    )
                    AmTextField(
                        icon = AmIcons.Money,
                        placeHolder = "Amount",
                        keyboardOptions = KeyboardOptions.Default.copy(
                            imeAction = ImeAction.Done, keyboardType = KeyboardType.Number,
                        ),
                        textFieldState = TextFieldState(),
                        isValidateInput = true
                    )
                    AmTextField(
                        placeHolder = "Transaction Description",
                        icon = AmIcons.SubTitle,
                        lineLimits = TextFieldLineLimits.Default,
                        textFieldState = TextFieldState(),
                        isValidateInput = true
                    )

//                    AmFilledTonalIconWithTextButton(
//                        modifier = Modifier.fillMaxWidth(),
//                        text = formattedTransactionAt,
//                        amIconsType = AmIcons.Date,
//                        positive = null,
//                        onClick = {
//                            transactionEditorState.showPickDateBottomSheet(
//                                initTime = transactionAt,
//                                setDate = transactionEditorState::updateTransactionAt
//                            )
//                        }
//                    )

                    AmChipsContainer(
                        title = stringResource(R.string.transaction_type),
                        chipDataList = transactionTypeChipData,
                        onSelect = { transactionEditorState.updateTransactionType(it.id) },
                        selectedItemID = selectedTransactionTypeID,
                        content = null
                    )
                }
            }
        }
    }

}

