package com.awesome.manager.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.awesome.manager.core.designsystem.component.AmSurface
import com.awesome.manager.core.designsystem.component.AmText
import com.awesome.manager.core.designsystem.icon.AmIcons
import kotlin.math.absoluteValue

@Composable
fun AccountCard(
    modifier: Modifier, title: String, imageUrl: String,
    creditor: Double, debtor: Double, currency: String,
    loading: Boolean,
    onClick: () -> Unit,
    onAddTransaction: (() -> Unit)?,
    onEditTransaction: (() -> Unit)?
) {

    val total = remember(creditor, debtor) { (creditor - debtor) }
    val positive = remember(total) { total >= 0 }

    AmCard(
        modifier = modifier,
        positive = positive,
        loading = loading,
        onClick = onClick,
        content = {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                AmImage(modifier = Modifier.size(50.dp), imageUrl = imageUrl)
                Spacer(modifier = Modifier.width(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    AmText(text = title, style = MaterialTheme.typography.titleLarge)
                    AmText(text = "$total $currency", style = MaterialTheme.typography.titleMedium)
                }
                onAddTransaction?.let {
                    Column {
                        AmIconButton(
                            modifier = Modifier,
                            amIconsType = AmIcons.TransactionAdd,
                            positive = null,
                            onClick = onAddTransaction
                        )
                    }
                }
                onEditTransaction?.let {
                    Column {
                        AmIconButton(
                            modifier = Modifier,
                            amIconsType = AmIcons.Edit,
                            positive = null,
                            onClick = onEditTransaction
                        )
                    }
                }
            }

            Spacer(
                modifier = Modifier.height(4.dp),
            )

//            AmSurface(positive=positive){
            Row {
                AmTitleWithSubtitle(
                    Modifier
                        .padding(horizontal = 4.dp)
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.pay),
                    subtitle = creditor.toString(),
                    amIconsType = AmIcons.Output,
                    positive = false
                )
                AmTitleWithSubtitle(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    title = stringResource(R.string.receive),
                    subtitle = debtor.toString(),
                    amIconsType = AmIcons.Input,
                    positive = true
                )
            }
//            }
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
        onClick = {}, onAddTransaction = {}, onEditTransaction = {}
    )
}