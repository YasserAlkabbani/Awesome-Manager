@file:OptIn(ExperimentalMaterial3Api::class)

package com.awesome.manager.feature.accounts

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.awesome.manager.core.designsystem.component.AmButton
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmModelBottomSheet
import com.awesome.manager.core.designsystem.component.AmTextField
import com.awesome.manager.core.designsystem.component.rememberAmSheetState
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.core.ui.CurrencyCard
import com.awesome.manager.core.ui.ScreenPlaceHolder
import kotlinx.coroutines.flow.StateFlow


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsRoute(
    clickFab: StateFlow<Unit?>,
    doneClickFab:()->Unit,
    accountsViewModel: AccountsViewModel= hiltViewModel()
){

    val accountsState=accountsViewModel.accountsState

    val clickFabAction=clickFab.collectAsStateWithLifecycle().value
    LaunchedEffect(key1 = clickFabAction, block = {
        clickFabAction?.let {
            doneClickFab()
            accountsState.startCreateAccountAction()
        }
    })

    val createAccountAmSheetState= rememberAmSheetState()
    val createAccountAction=accountsState.createAccountAction.collectAsState().value
    LaunchedEffect(key1 = createAccountAction, block = {
        createAccountAction?.let {
            accountsState.doneCreateAccountAction()
            createAccountAmSheetState.open()
        }
    })

    val currency=accountsState.currency.collectAsState().value
    val name=accountsState.name.collectAsState().value
    val imageUrl=accountsState.imageUrl.collectAsState().value
    AmModelBottomSheet(
        amSheetState = createAccountAmSheetState,
        content = {
            Surface(Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row {
                        AmImage(modifier = Modifier.size(40.dp), imageUrl = imageUrl)
                        AmTextField(text = name, onTextChange = accountsState::updateName)
                    }
                    AnimatedVisibility(visible = currency!=null) {
                        if (currency!=null)
                            CurrencyCard(
                                modifier = Modifier,
                                countryName = currency.countryName,
                                currencyName = currency.currencyName,
                                currencySympl = currency.currencySymbol,
                                imageUrl = currency.imageUrl,
                                onClick = {}
                            )
                    }
                    Text(text = "")
                    AmButton(
                        text = "Create",
                        onClick = createAccountAmSheetState::close
                    )
                }
            }
        }
    )

    AccountsScreen()
}


@Composable
fun AccountsScreen(){
    ScreenPlaceHolder(
        title = "ACCOUNTS",
        textButton = "SHOW BOTTOM SHEET",
        onClickButton = {}
    )
}