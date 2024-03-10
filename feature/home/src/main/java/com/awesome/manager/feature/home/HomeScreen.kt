package com.awesome.manager.feature.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Devices.PIXEL_4_XL
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.awesome.manager.core.common.states.DataState
import com.awesome.manager.core.designsystem.UIConstant
import com.awesome.manager.core.designsystem.UIConstant.LOW_PADDING
import com.awesome.manager.core.designsystem.UIConstant.VERY_HIGH_PADDING
import com.awesome.manager.core.designsystem.ui_actions.MainActions
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmSpacerMediumWidth
import com.awesome.manager.core.designsystem.component.AmSpacerSmallHight
import com.awesome.manager.core.designsystem.component.AmSpacerSmallWidth
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.model.CurrencyWithBalance
import com.awesome.manager.core.ui.AmTextWithIcon
import com.awesome.manager.core.ui.AmTextWithIconLarge
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import timber.log.Timber
import kotlin.math.absoluteValue

@Composable
fun HomeRoute(
    sendMainAction: (MainActions) -> Unit,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val homeState = homeViewModel.homeState
    HomeScreen(homeState)
}


@Composable
fun HomeScreen(homeState: HomeState) {

    val currencyWithBalance = homeState.currencyWithData.collectAsState().value

    if (currencyWithBalance is DataState.Success) {
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(LOW_PADDING.dp),
            contentPadding = PaddingValues(
                start = LOW_PADDING.dp,
                end = LOW_PADDING.dp,
                bottom = VERY_HIGH_PADDING.dp
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
                positiveValue = currencyWithBalance.lent ,
                positiveLabel = stringResource(id = R.string.lent),
                negativeValue = currencyWithBalance.borrow ,
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
    val (isPositiveNetValue,absoluteNetValue) = remember {
        derivedStateOf {
            when{
                netValue >= 0 ->true to netValue.absoluteValue
                else->false to netValue.absoluteValue
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
            AmSpacerSmallHight()
            AmTextWithIconLarge(
                modifier = Modifier.fillMaxWidth(),
                text = negativeValue.toString(),
                amIconsType = AmIcons.Output, positive = false
            )
            AmSpacerSmallHight()
            AmTextWithIconLarge(
                modifier = Modifier.fillMaxWidth(),
                text = absoluteNetValue.toString(),
                amIconsType = AmIcons.Balance, positive = isPositiveNetValue
            )
        }
    }
}