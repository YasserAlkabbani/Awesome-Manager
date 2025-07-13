package com.awesome.manager.core.designsystem.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.R

fun Enum<*>.enumToRes(): Int? =
    when (name) {
        "INCOME" -> R.string.income
        "EXPENSES" -> R.string.expenses
        "DEBTOR" -> R.string.debtor
        "CREDITOR" -> R.string.creditor
        "ACCOUNT" -> R.string.account
        "TRANSACTION" -> R.string.transaction
        else -> null
    }

@Composable
fun Enum<*>.getString() =
    this.enumToRes()?.let { stringResource(it) }?:name
