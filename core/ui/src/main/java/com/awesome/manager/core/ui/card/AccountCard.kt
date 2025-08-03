package com.awesome.manager.core.ui.card

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.awesome.manager.core.designsystem.AmPadding
import com.awesome.manager.core.designsystem.component.surface.AmSurface
import com.awesome.manager.core.designsystem.component.text.AmText


@Composable
fun AccountCardWithDetails(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    loading: Boolean,
    balanceData: BalanceData,
) {
    AmSurface(
        modifier = modifier.fillMaxWidth(),
        isLoading = loading,
        content = {
            Column(modifier = Modifier) {
                AmText(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textStyle = MaterialTheme.typography.displaySmall,
                    amTextPadding = AmPadding.ZERO
                )
                AmBalanceDetailsCard(balanceData = balanceData)
            }
        }
    )
}

@Composable
fun AccountCard(
    modifier: Modifier,
    title: String,
    imageUrl: String,
    loading: Boolean,
    balanceData: BalanceData,
    onClick: () -> Unit
) {
    AmSurface(
        modifier = modifier.fillMaxWidth(),
        isLoading = loading,
        onClick = onClick,
        content = {
            Column(modifier = Modifier) {
                AmText(
                    modifier = Modifier.fillMaxWidth(),
                    text = title,
                    textStyle = MaterialTheme.typography.displaySmall,
                    amTextPadding = AmPadding.ZERO
                )
                AmBalanceCard(balanceData = balanceData)
            }
        }
    )
}


//@Composable
//fun AccountBasic(
//    title: String,
//    imageUrl: String,
//    currencyCode: String,
//) {
//    Row(
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        AmImage(
//            modifier = Modifier.size(AmSize.ACCOUNT_IMAGE_SIZE.value),
//            imageUrl = imageUrl
//        )
//        AmText(
//            modifier = Modifier.fillMaxWidth().weight(1f),
//            text = title,
//            textStyle = MaterialTheme.typography.displaySmall
//        )
//    }
//}


@Preview
@Composable
fun AccountCardPreview() {
    AccountCard(
        modifier = Modifier,
        title = "Account Name",
        imageUrl = "",
        loading = true,
        balanceData = BalanceData.generate(
            creditor = "100.0",
            debtor = "600.0",
            netDebtorAbs = "3000.0",
            isPositiveDebtor = true,
            income = "500.0",
            expenses = "300.0",
            netIncomeAbs = "5000.0",
            isPositiveIncome = false,
            currencyCode = "USD",
        ),
        onClick = {},
    )
}

@Preview
@Composable
fun AccountCardWithDetailsPreview() {
    AccountCardWithDetails(
        modifier = Modifier,
        title = "Account Name",
        imageUrl = "",
        loading = true,
        balanceData = BalanceData.generate(
            creditor = "100.0",
            debtor = "600.0",
            netDebtorAbs = "3000.0",
            isPositiveDebtor = true,
            income = "500.0",
            expenses = "300.0",
            netIncomeAbs = "5000.0",
            isPositiveIncome = false,
            currencyCode = "USD",
        ),
    )
}