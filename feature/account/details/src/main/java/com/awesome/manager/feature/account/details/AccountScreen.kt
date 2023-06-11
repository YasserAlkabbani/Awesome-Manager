package com.awesome.manager.feature.account.details

import androidx.compose.runtime.Composable

@Composable
fun AccountRoute(){
    AccountScreen()
}

@Composable
fun AccountScreen(){

//    val currency=accountsState.currency.collectAsState().value
//    val name=accountsState.name.collectAsState().value
//    val imageUrl=accountsState.imageUrl.collectAsState().value
//    AmModelBottomSheet(
//        amSheetState = createAccountAmSheetState,
//        content = {
//            Surface(Modifier.fillMaxWidth()) {
//                Column(modifier = Modifier.fillMaxWidth()) {
//                    Row {
//                        AmImage(modifier = Modifier.size(40.dp), imageUrl = imageUrl)
//                        AmTextField(text = name, onTextChange = accountsState::updateName)
//                    }
//                    AnimatedVisibility(visible = currency!=null) {
//                        if (currency!=null)
//                            CurrencyCard(
//                                modifier = Modifier,
//                                countryName = currency.countryName,
//                                currencyName = currency.currencyName,
//                                currencySympl = currency.currencySymbol,
//                                imageUrl = currency.imageUrl,
//                                onClick = {}
//                            )
//                    }
//                    Text(text = "")
//                    AmButton(
//                        text = "Create",
//                        onClick = createAccountAmSheetState::close
//                    )
//                }
//            }
//        }
//    )

}