package com.awesome.manager.core.model


enum class AmTransactionType(val posative: Boolean) {
    INCOME(posative = true),
    EXPENSES(posative = false),
    DEBTOR(posative = true),
    CREDITOR(posative = false)
}

data class BalanceDetails(
    val currency: AmCurrency,
    val income: Double,
    val expenses: Double,
    val debtor: Double,
    val creditor: Double,
    val netIncome: Double = income - expenses,
    val netDebtor: Double = debtor - creditor,
    val currentCash: Double = netIncome + netDebtor,
    val currentBalance: Double = income - netDebtor
)