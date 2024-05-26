package com.awesome.manager.core.data.model

import com.awesome.manager.core.database.model.TransactionTypeEntity
import com.awesome.manager.core.model.AmTransactionType

fun TransactionTypeEntity.asModel(): AmTransactionType = when (this) {
    TransactionTypeEntity.EXPENSES -> AmTransactionType.EXPENSES
    TransactionTypeEntity.INCOME -> AmTransactionType.INCOME
    TransactionTypeEntity.DEBTOR -> AmTransactionType.DEBTOR
    TransactionTypeEntity.CREDITOR -> AmTransactionType.CREDITOR
}

fun AmTransactionType.asEntity(): TransactionTypeEntity = when (this) {
    AmTransactionType.EXPENSES -> TransactionTypeEntity.EXPENSES
    AmTransactionType.INCOME -> TransactionTypeEntity.INCOME
    AmTransactionType.DEBTOR -> TransactionTypeEntity.DEBTOR
    AmTransactionType.CREDITOR -> TransactionTypeEntity.CREDITOR
}