package com.awesome.manager.core.designsystem.text

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.awesome.manager.core.designsystem.R

fun Context.enumToString(const: Enum<*>): String =
    when (const.name) {
        "INCOME" -> getString(R.string.income)
        "EXPENSES" -> getString(R.string.expenses)
        "DEBTOR" -> getString(R.string.debtor)
        "CREDITOR" -> getString(R.string.creditor)
        else -> const.name
    }

@Composable
fun Enum<*>.getString() =
    LocalContext.current.enumToString(this)
