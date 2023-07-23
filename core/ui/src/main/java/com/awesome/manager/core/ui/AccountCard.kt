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
import com.awesome.manager.core.designsystem.component.buttons.AmIconButton
import com.awesome.manager.core.designsystem.component.AmImage
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import kotlin.math.absoluteValue

@Composable
fun AccountCard(
    modifier: Modifier, title: String, imageUrl: String,
    creditor: Double, debtor: Double, currency: String,
    loading:Boolean,
    onClick: () -> Unit,
    onAddTransaction:(()->Unit)?,
    onEditTransaction:(()->Unit)?
) {
    
    val total = remember(creditor, debtor) { (creditor - debtor).absoluteValue }

    AmCard(
        modifier = modifier,
        positive=total>=0,loading = loading,
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                AmImage(modifier = Modifier.size(42.dp), imageUrl = imageUrl)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    AmText(text = title, style = MaterialTheme.typography.titleMedium)
                    AmText(text = "$total $currency", style = MaterialTheme.typography.labelLarge)
                }
                onAddTransaction?.let {
                    Column {
                        AmIconButton(amIconsType = AmIcons.TransactionAdd, onClick = onAddTransaction)
                    }
                }
                onEditTransaction?.let {
                    Column {
                        AmIconButton(amIconsType = AmIcons.Edit, onClick = onEditTransaction)
                    }
                }
            }
            Row {
                AmTitleWithSubtitle(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.creditor),
                    subtitle = creditor.toString()
                )
                AmTitleWithSubtitle(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.debtor),
                    subtitle = debtor.toString()
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
        loading = true,
        onClick = {}, onAddTransaction = {},onEditTransaction = {}
    )
}