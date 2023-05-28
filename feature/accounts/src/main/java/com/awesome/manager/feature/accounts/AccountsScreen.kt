@file:OptIn(ExperimentalMaterial3Api::class)

package com.awesome.manager.feature.accounts

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.designsystem.component.AmButton
import com.awesome.manager.core.designsystem.component.AmModelBottomSheet
import com.awesome.manager.core.designsystem.component.rememberAmSheetState
import com.awesome.manager.core.designsystem.text.asAmText
import com.awesome.manager.core.ui.ScreenPlaceHolder


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AccountsRoute(accountsViewModel: AccountsViewModel= hiltViewModel()){


    val amSheetState= rememberAmSheetState()

    AccountsScreen(amSheetState::open)

    AmModelBottomSheet(
        amSheetState = amSheetState,
        content = {
            Surface(Modifier.fillMaxWidth()) {
                AmButton(
                    amText = "HIDE".asAmText(),
                    onClick = amSheetState::close
                )
            }
        }
    )

}


@Composable
fun AccountsScreen(showBottomSheet:()->Unit){
    ScreenPlaceHolder(
        title = "ACCOUNTS",
        textButton = "SHOW BOTTOM SHEET",
        onClickButton = showBottomSheet
    )
}