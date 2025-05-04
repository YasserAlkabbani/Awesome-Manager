package com.awesome.manager.feature.account.editor

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.common.dataOrNull
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.AmSize
import com.awesome.manager.core.designsystem.component.AMHorizontalFloatingToolbar
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.FTBButtonData
import com.awesome.manager.core.designsystem.component.text.AmTextField
import com.awesome.manager.core.designsystem.component.text.isValidAccountName
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToString
import com.awesome.manager.core.model.AmCurrency
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData

@Composable
internal fun AccountEditorRoute(
    accountEditorViewModel: AccountEditorViewModel = hiltViewModel(),
) {

    val accountNameTextFieldState = accountEditorViewModel.accountNameTextFieldState
    val transactionTypes = accountEditorViewModel.transactionTypes

    val currencies =
        accountEditorViewModel.currencies.collectAsStateWithLifecycle().value.dataOrNull()

    val accountImageUrl = accountEditorViewModel.accountImageURL.collectAsStateWithLifecycle().value
    val selectedCurrencyID = accountEditorViewModel.currencyID.collectAsStateWithLifecycle().value
    val selectedTransactionTypeID =
        accountEditorViewModel.transactionTypeID.collectAsStateWithLifecycle().value

    val setCurrencyID = accountEditorViewModel::setCurrencyID
    val setTransactionTypeID = accountEditorViewModel::setTransactionTypeID


    AccountEditorScreen(
        accountNameTextFieldState = accountNameTextFieldState,
        currencies = currencies,
        transactionTypes = transactionTypes,
        accountImageUrl = accountImageUrl,
        selectedCurrencyID = selectedCurrencyID,
        setCurrencyID = setCurrencyID,
        selectedTransactionTypeID = selectedTransactionTypeID,
        setTransactionTypeID = setTransactionTypeID,
    )

}

@Composable
internal fun AccountEditorScreen(
    accountNameTextFieldState: TextFieldState,
    currencies: List<AmCurrency>?,
    transactionTypes: List<AmTransactionType>,
    accountImageUrl: String?,
    selectedCurrencyID: String?,
    setCurrencyID: (String) -> Unit,
    selectedTransactionTypeID: String?,
    setTransactionTypeID: (String) -> Unit
) {
    val context = LocalContext.current
    val currencyChipData = remember(currencies) {
        currencies.orEmpty().map { ChipData(id = it.id, title = it.currencyName) }
    }
    val transactionTypeChipData = remember {
        transactionTypes.map {
            val title = context.enumToString(it)
            ChipData(id = it.id, title = title)
        }
    }
    val isValidAccountName: Boolean =remember(accountNameTextFieldState.text) {
        accountNameTextFieldState.isValidAccountName()
    }

    Box(modifier = Modifier.fillMaxSize()) {
        AMHorizontalFloatingToolbar(
            text = when (isValidAccountName) {
                true -> stringResource(R.string.validated_input)
                else -> stringResource(R.string.invalid_input)
            },
            mainButton = FTBButtonData(
                text = "",
                amIconsType = AmIcons.ArrowBack,
                onClick = {}
            ),
            validationButton = FTBButtonData(
                text = stringResource(R.string.create),
                amIconsType = AmIcons.Save,
                onClick = {}
            ),
            isError = !isValidAccountName,
        )
        Column(
            modifier = Modifier
                .fillMaxSize(),
            //            .padding(top = AmPadding.TOP_PADDING.value)
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(AmPadding.COULMN_ITEMS_PADDING.value),
        ) {
            AmImage(
                modifier = Modifier
                    .padding(AmPadding.LARGE_IMAGE_PADDING.value)
                    .size(AmSize.LARGE_IMAGE_SIZE.value),
                imageUrl = accountImageUrl
            )
            AmTextField(
                modifier = Modifier
                    .padding(horizontal = AmPadding.HORIZONTAL_PADDING.value)
                    .fillMaxWidth(),
                textFieldState = accountNameTextFieldState,
                label = "Account Name",
                icon = AmIcons.Title,
                hint = "New Account",
                isError = !isValidAccountName
            )
            AmChipsContainer(
                title = "Currency",
                chipDataList = currencyChipData,
                selectedItem = selectedCurrencyID,
                onSelect = { setCurrencyID(it.id) },
                content = null
            )
            AmChipsContainer(
                title = stringResource(R.string.default_transaction_type),
                chipDataList = transactionTypeChipData,
                selectedItem = selectedTransactionTypeID,
                onSelect = { setTransactionTypeID(it.id) },
                content = null
            )
        }


    }

}