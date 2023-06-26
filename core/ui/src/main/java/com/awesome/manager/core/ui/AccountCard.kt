package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.awesome.manager.core.designsystem.component.AmCard
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmText

@Composable
fun AccountCard(
    modifier: Modifier, title: String, imageUrl: String,
    creditor: Double, debtor: Double,currency:String,
    onClick: () -> Unit
) {

    val primaryContainer = MaterialTheme.colorScheme.primaryContainer
    val errorContainer = MaterialTheme.colorScheme.errorContainer
    val total = remember(creditor, debtor) { creditor - debtor }
    val cardColor = remember(total) { if (total > 0) primaryContainer else errorContainer }

    AmCard(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = cardColor),
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                AmImage(modifier = Modifier.size(30.dp), imageUrl = imageUrl)
                Spacer(modifier = Modifier.width(8.dp))
                AmText(text = title, style = MaterialTheme.typography.titleLarge)
            }
            Row {
                AmTitleWithSubtitle(
                    modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.creditor),
                    subtitle = creditor.toString()
                )
                AmTitleWithSubtitle(
                    modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.debtor),
                    subtitle = debtor.toString()
                )
                AmTitleWithSubtitle(
                    modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.total),
                    subtitle = total.toString()
                )
                AmTitleWithSubtitle(
                    modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.currency),
                    subtitle = currency
                )
            }
        }
    )

}

@Preview
@Composable
fun AccountCardPreview() {
    AccountCard(
        modifier = Modifier.width(400.dp),
        title = "TITLE", imageUrl = "",
        creditor = 15000.0, debtor = 6000.0, currency = "$",
        onClick = {}
    )
}