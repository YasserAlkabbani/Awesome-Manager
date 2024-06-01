package com.awesome.manager.core.model

import kotlin.math.absoluteValue


enum class AmTransactionType(val posative: Boolean) {
    INCOME(posative = true),
    EXPENSES(posative = false),
    DEBTOR(posative = true),
    CREDITOR(posative = false)
}

data class BalanceDetails(
    val income: Double, val expenses: Double,
    val debtor: Double, val creditor: Double,
    val currency: AmCurrency,
) {
    private val netIncome: Double = income - expenses
    private val netDebtor: Double = debtor - creditor
    private val input: Double = income + debtor
    private val output: Double = expenses + creditor
    private val netCash: Double = input - output

    val netIncomeAbs: Double = netIncome.absoluteValue
    val netDebtorAbs: Double = netDebtor.absoluteValue
    val netCashAbs: Double = netCash.absoluteValue
    val isPositiveCash: Boolean = netCash >= 0
    val isPositiveIncome: Boolean = netIncome >= 0
    val isPositiveDebtor: Boolean = netDebtor <= 0

}