package com.awesome.manager.feature.transaction.editor

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
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.model.AmAccountWithDetails
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.ui.ChipData
import com.awesome.manager.core.ui.card.AccountCardWithDetails
import com.awesome.manager.core.ui.card.BalanceData

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TransactionEditorScreen(
//    sendMainAction: (MainAction) -> Unit,
    transactionEditorViewModel: TransactionEditorViewModel = hiltViewModel(),
) {

    val transactionEditorState: TransactionEditorState =
        transactionEditorViewModel.transactionEditorState.collectAsStateWithLifecycle().value
    val accountWithBalance: AmAccountWithDetails? =
        transactionEditorViewModel.accountWithDetails.collectAsStateWithLifecycle().value


    val titleTextFieldState = transactionEditorViewModel.titleTextFieldState
    val subTitleTextFieldState = transactionEditorViewModel.subTitleTextFieldState
    val amountTextFieldState = transactionEditorViewModel.amountTextFieldState

//    val mainAction = transactionEditorState.mainAction.collectAsState().value
//    LaunchedEffect(key1 = mainAction) {
//        mainAction?.sendMainAction(sendMainAction, transactionEditorState::doneMainAction)
//    }

    TransactionEditorScreen(
        transactionEditorState = transactionEditorState,
        accountWithBalance = accountWithBalance,
        titleTextFieldState = titleTextFieldState,
        subTitleTextFieldState = subTitleTextFieldState,
        amountTextFieldState = amountTextFieldState
    )
}

@Composable
internal fun TransactionEditorScreen(
    transactionEditorState: TransactionEditorState,
    accountWithBalance: AmAccountWithDetails?,
    titleTextFieldState: TextFieldState,
    subTitleTextFieldState: TextFieldState,
    amountTextFieldState: TextFieldState
) {

    val transactionEditorData: TransactionEditorState = transactionEditorState

//    val transactionTypeChipData = remember {
//        AmTransactionType.entries.map {
//            ChipData(id = it.id, titleRes = it.enumToRes(), title = it.name)
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        accountWithBalance?.let { accountWithDetails ->
            val account = accountWithDetails.account
            AccountCardWithDetails(
                modifier = Modifier,
                title = account.name, imageUrl = account.imageUrl,
                loading = account.pending,
                balanceData = BalanceData.generate(
                    debtor = accountWithDetails.formattedDebtor,
                    creditor = accountWithDetails.formattedCreditor,
                    netDebtorAbs = accountWithDetails.formattedNetDebtor,
                    isPositiveDebtor = accountWithDetails.isPositiveDebtor,
                    income = accountWithDetails.formattedIncome,
                    expenses = accountWithDetails.formattedExpenses,
                    netIncomeAbs = accountWithDetails.formattedNetIncome,
                    isPositiveIncome = accountWithDetails.isPositiveIncome,
                    currencySymbol = accountWithDetails.currencySymbol,
                )
            )
        }

        AmTextField(
            modifier = Modifier,
            placeHolder = "Title",
            icon = AmIcons.Title,
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
            textFieldState = titleTextFieldState,
            isValidateInput = true
        )
        AmTextField(
            icon = AmIcons.Money,
            placeHolder = "Amount",
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            ),
            textFieldState = amountTextFieldState,
            isValidateInput = true
        )
        AmTextField(
            placeHolder = "Transaction Description",
            icon = AmIcons.SubTitle,
            lineLimits = TextFieldLineLimits.Default,
            textFieldState = subTitleTextFieldState,
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

//        AmChipsContainer(
//            title = stringResource(R.string.transaction_type),
//            chipDataList = transactionTypeChipData,
//            onSelect = { transactionEditorState.updateTransactionType(it.id) },
//            selectedItemID = selectedTransactionTypeID,
//            content = null
//        )
    }

}

