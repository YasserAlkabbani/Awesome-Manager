package com.awesome.manager.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.UIConstant.PADDING_LOW
import com.awesome.manager.core.designsystem.UIConstant.PADDING_LARGE_EXTRA
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHeight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallWidth
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.model.CurrencyWithBalance
import com.awesome.manager.core.ui.AmTextWithIconLarge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlin.math.absoluteValue

@Composable
fun HomeRoute(
    sendMainAction: (MainActions) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState = homeViewModel.homeState

    val navigationAction =
        homeState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, homeState::resetNavigationAction)
    })

    val appBarAction =
        homeState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, homeState::resetAppBar)
    })

    val bottomSheetAction =
        homeState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction,homeState::idleBottomSheet)
    })



//    when (homeState.currencyWithData.collectAsState().value) {
//        is DataState.Success, DataState.Error, DataState.Loading ->
//            homeState.showMainAppBar(
//                onAddAccount = homeState::navigateToCreateAccount,
//                onAddTransaction = { homeState.navigateToCreateTransaction(null) }
//            )
//    }

    HomeScreen(homeState)
}


@Composable
fun HomeScreen(homeState: HomeState) {

    when (val currencyWithBalance = homeState.currencyWithData.collectAsState().value) {
        is DataState.Success -> LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(PADDING_LOW.dp),
            contentPadding = PaddingValues(
                start = PADDING_LOW.dp,
                end = PADDING_LOW.dp,
                bottom = PADDING_LARGE_EXTRA.dp
            ),
            content = {
                items(
                    items = currencyWithBalance.data,
                    contentType = { "HOME_CARD" },
                    key = { it.amCurrency.id },
                    itemContent = {
                        HomeCard(currencyWithBalance = it)
                    }
                )
            })

        DataState.Error -> {
            Column(
                modifier = Modifier
                    .padding(PADDING_LARGE_EXTRA.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AmText(
                    text = stringResource(R.string.theres_no_accounts_yet),
                    maxLines = 3, textAlign = TextAlign.Center
                )
                AmFilledTonalButton(
                    text = stringResource(R.string.create_an_account),
                    onClick = homeState::navigateToCreateAccount,
                    positive = null
                )
            }
        }

        DataState.Loading -> {}
    }
}

@Preview(device = PIXEL_4_XL)
@Composable
fun HomeScreenPreview() {
    HomeScreen(HomeState(currencyWithData = MutableStateFlow(DataState.Success(listOf()))))
}

@Composable
fun HomeCard(currencyWithBalance: CurrencyWithBalance) {
    val positiveCash = remember { derivedStateOf { currencyWithBalance.netCash >= 0 } }.value
    AmSurface(modifier = Modifier.fillMaxWidth(), highPadding = false, positive = positiveCash) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AmText(
                text = currencyWithBalance.amCurrency.currencyCode,
                style = MaterialTheme.typography.titleLarge
            )
            AmText(
                text = "${currencyWithBalance.netCash} ${currencyWithBalance.amCurrency.currencySymbol}",
                style = MaterialTheme.typography.titleLarge
            )
        }
        Row {
            HomeCardResults(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                positiveValue = currencyWithBalance.lent,
                positiveLabel = stringResource(id = R.string.lent),
                negativeValue = currencyWithBalance.borrow,
                negativeLabel = stringResource(id = R.string.borrow),
                netValue = currencyWithBalance.netLent
            )
            AmSpacerSmallWidth()
            HomeCardResults(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                positiveValue = currencyWithBalance.incoming,
                positiveLabel = stringResource(id = R.string.incoming),
                negativeValue = currencyWithBalance.outgoing,
                negativeLabel = stringResource(id = R.string.outgoing),
                netValue = currencyWithBalance.netIncoming
            )
        }
    }
}

@Composable
fun HomeCardResults(
    modifier: Modifier,
    positiveValue: Double,
    positiveLabel: String,
    negativeValue: Double,
    negativeLabel: String,
    netValue: Double
) {
    val (isPositiveNetValue, absoluteNetValue) = remember {
        derivedStateOf {
            when {
                netValue >= 0 -> true to netValue.absoluteValue
                else -> false to netValue.absoluteValue
            }
        }
    }.value
    AmCard(modifier = modifier, positive = isPositiveNetValue) {
        Column {
            AmText(text = "$positiveLabel/$negativeLabel")
            AmTextWithIconLarge(
                modifier = Modifier.fillMaxWidth(),
                text = positiveValue.toString(),
                amIconsType = AmIcons.Input, positive = true,
            )
            AmSpacerSmallHeight()
            AmTextWithIconLarge(
                modifier = Modifier.fillMaxWidth(),
                text = negativeValue.toString(),
                amIconsType = AmIcons.Output, positive = false
            )
            AmSpacerSmallHeight()
            AmTextWithIconLarge(
                modifier = Modifier.fillMaxWidth(),
                text = absoluteNetValue.toString(),
                amIconsType = AmIcons.Balance, positive = isPositiveNetValue
            )
        }
    }
}