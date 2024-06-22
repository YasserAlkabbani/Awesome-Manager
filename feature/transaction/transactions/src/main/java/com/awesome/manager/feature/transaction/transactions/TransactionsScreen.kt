package com.awesome.manager.feature.transaction.transactions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.awesome.manager.core.common.extentions.currentTime
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.actions.appbar.sendMainAction
import com.awesome.manager.core.designsystem.actions.bottomsheet.sendMainAction
import com.awesome.manager.core.designsystem.actions.main.MainAction
import com.awesome.manager.core.designsystem.actions.navigation.sendMainAction
import com.awesome.manager.core.designsystem.component.text.AmText
import com.awesome.manager.core.designsystem.component.buttons.AmFilledTonalButton
import com.awesome.manager.core.designsystem.component.chips.AmFilterChip
import com.awesome.manager.core.designsystem.icon.AmIcons
import com.awesome.manager.core.designsystem.text.enumToString
import com.awesome.manager.core.designsystem.text.getString
import com.awesome.manager.core.model.AmTransactionType
import com.awesome.manager.core.ui.AmChipsContainer
import com.awesome.manager.core.ui.ChipData
import com.awesome.manager.core.ui.card.TransactionCard
import com.awesome.manager.core.ui.getChipData
import com.awesome.manager.core.ui.lazy_column.AmLazyColumn
import com.awesome.manager.core.ui.lazy_column.LAZY_ITEM_TRANSACTION


@Composable
fun TransactionsRoute(
    sendMainAction: (MainAction) -> Unit,
    transactionsViewModel: TransactionsViewModel = hiltViewModel()
) {

    val transactionsState = transactionsViewModel.transactionsState

    val navigationAction = transactionsState.navigationAction.collectAsState().value
    LaunchedEffect(key1 = navigationAction, block = {
        navigationAction.sendMainAction(sendMainAction, transactionsState::doneNavigationAction)
    })

    val appBarAction = transactionsState.appBarAction.collectAsState().value
    LaunchedEffect(key1 = appBarAction, block = {
        appBarAction.sendMainAction(sendMainAction, transactionsState::doneAppBarAction)
    })

    val bottomSheetAction = transactionsState.bottomSheetAction.collectAsState().value
    LaunchedEffect(key1 = bottomSheetAction, block = {
        bottomSheetAction.sendMainAction(sendMainAction, transactionsState::doneBottomSheetAction)
    })

    TransactionScreen(transactionsState)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(transactionsState: TransactionsMainState) {

    val context = LocalContext.current
    val isLoading = transactionsState.isLoading.collectAsState().value
    val transactionsLazyPaging = transactionsState.transactions.collectAsLazyPagingItems()
    val filterData by transactionsState.filterData.collectAsState()
    val noItems = remember {
        derivedStateOf {
            !filterData.filterApplauded && transactionsLazyPaging.itemCount == 0
        }
    }.value

    val transactionTypeChipData = remember {
        transactionsState.transactionTypes.map {
            getChipData(id = it.name, title = context.enumToString(it), data = it)
        }
    }

    Column(Modifier.fillMaxSize()) {
        if (noItems)
            Column(
                modifier = Modifier
                    .padding(AmPadding.X_LARGE.value)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AmText(
                    text = stringResource(R.string.theres_no_transactions_yet),
                    maxLines = 3, textAlign = TextAlign.Center
                )
                AmFilledTonalButton(
                    text = stringResource(R.string.create_a_transaction),
                    onClick = { transactionsState.navigateToCreateTransaction(null) },
                )
            }
        else {
            AmLazyColumn(
                isRefreshing = isLoading,
                onRefresh = transactionsState.refreshTransactions,
                content = {
                    item(contentType = "FILTER", key = "FILTER") {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
                                AmFilterChip(
                                    selected = filterData.searchFilter,
                                    label = stringResource(R.string.search),
                                    value = filterData.searchKey,
                                    amIconsType = AmIcons.Search,
                                    onClick = {
                                        searchAndFilter(
                                            transactionsState = transactionsState,
                                            searchLabel = context.getString(R.string.search_in_transactions),
                                            filterData = { filterData },
                                            transactionTypeChipData = transactionTypeChipData,
                                        )
                                    },
                                    onRemove = transactionsState::clearSearch
                                )
                                AmFilterChip(
                                    selected = filterData.transactionTypeFilter,
                                    label = stringResource(R.string.transaction_type),
                                    value = filterData.transactionType?.name,
                                    amIconsType = AmIcons.Category,
                                    onClick = {
                                        searchAndFilter(
                                            transactionsState = transactionsState,
                                            searchLabel = context.getString(R.string.search_in_transactions),
                                            filterData = { filterData },
                                            transactionTypeChipData = transactionTypeChipData,
                                        )
                                    },
                                    onRemove = transactionsState::clearTransactionType
                                )
                                AmFilterChip(
                                    selected = filterData.dateFilter,
                                    label = stringResource(R.string.date),
                                    value = filterData.dateString,
                                    amIconsType = AmIcons.Date,
                                    onClick = {
                                        transactionsState.showPickRangeDateBottomSheet(
                                            initTime = currentTime(),
                                            setDate = transactionsState::updateDate,
                                            dismiss = transactionsState::dismissBottomSheet
                                        )
                                    },
                                    onRemove = transactionsState::clearDate
                                )
                            }
                        }
                    }
                    items(
                        count = transactionsLazyPaging.itemCount,
                        key = transactionsLazyPaging.itemKey { transaction -> transaction.id },
                        contentType = { LAZY_ITEM_TRANSACTION },
                        itemContent = { index ->
                            transactionsLazyPaging[index]?.let { transaction ->
                                TransactionCard(
                                    modifier = Modifier.animateItemPlacement(),
                                    account = transaction.accountName,
                                    title = transaction.title,
                                    amount = transaction.formattedAmount,
                                    pending = transaction.pending,
                                    date = transaction.transactionAtDate,
                                    transactionType = transaction.transactionType.getString(),
                                    isPay = transaction.transactionType.positive,
                                    currency = transaction.currency.currencyCode,
                                    onClick = {
                                        transactionsState.navigateToTransactionDetails(
                                            transaction.id
                                        )
                                    }
                                )
                            }
                        }
                    )
                },
            )
        }
    }
}

private fun searchAndFilter(
    transactionsState: TransactionsMainState,
    searchLabel: String,
    filterData: () -> FilterData,
    transactionTypeChipData: List<ChipData<AmTransactionType, String>>
) {
    transactionsState.showSearchWithContentBottomSheet(
        searchLabel = searchLabel,
        initSearch = filterData().searchKey.orEmpty(),
        onReSearch = transactionsState::updateSearchKey,
        onSearchDone = transactionsState::dismissBottomSheet,
        content = {
            AmChipsContainer(
                title = stringResource(R.string.transaction_type),
                chipDataList = transactionTypeChipData,
                onSelect = {
                    transactionsState.updateTransactionType(it.data)
                },
                selectedItem = filterData().transactionType?.name,
                content = null
            )
        }
    )
}