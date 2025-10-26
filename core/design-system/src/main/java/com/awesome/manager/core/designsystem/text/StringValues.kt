package com.awesome.manager.core.designsystem.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.R

fun String.asStringRes(): Int? =
    when (this.uppercase()) {
        "INCOME" -> R.string.income
        "EXPENSES" -> R.string.expenses
        "DEBTOR" -> R.string.debtor
        "CREDITOR" -> R.string.creditor
        "ACCOUNT" -> R.string.account
        "TRANSACTION" -> R.string.transaction
        else -> null
    }

@Composable
fun String.asString() =
    this.asStringRes()?.let { stringResource(it) } ?: this
