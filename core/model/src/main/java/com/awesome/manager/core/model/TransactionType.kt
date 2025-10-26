package com.awesome.manager.core.model

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue
import kotlin.random.Random

//enum class AmTransactionType(val positive: Boolean) {
//    INCOME(positive = true),
//    EXPENSES(positive = false),
//    DEBTOR(positive = true),
//    CREDITOR(positive = false);
//}

data class AmTransactionType(
    val id: String,
    val type: String,
    val isPositive: Boolean,
    val isClose: Boolean,
    val createdAt: Long
) {
    companion object {
        fun create() = AmTransactionType(
            id ="12345",
            type ="Income",
            isPositive =true,
            isClose =true,
            createdAt = System.currentTimeMillis(),
        )
    }
}

//data class BalanceDetails(
//    private val income: Double,
//    private val expenses: Double,
//    private val debtor: Double,
//    private val creditor: Double,
//    val currency: AmCurrency,
//) {
//
//    private fun Double.asFormatted() = NumberFormat.getNumberInstance(Locale.US).format(this)
//
//    private val netIncome: Double = income - expenses
//    private val netDebtor: Double = debtor - creditor
//    private val input: Double = income + debtor
//    private val output: Double = expenses + creditor
//    private val netCash: Double = input - output
//
//
//    val formattedIncome: String = income.asFormatted()
//    val formattedExpenses: String = expenses.asFormatted()
//    val formattedDebtor: String = debtor.asFormatted()
//    val formattedCreditor: String = creditor.asFormatted()
//
//    val formattedNetIncome: String = netIncome.absoluteValue.asFormatted()
//    val formattedNetDebtor: String = netDebtor.absoluteValue.asFormatted()
//    val formattedNetCash: String = netCash.absoluteValue.asFormatted()
//
//    val isPositiveCash: Boolean = netCash >= 0
//    val isPositiveIncome: Boolean = netIncome >= 0
//    val isPositiveDebtor: Boolean = netDebtor >= 0
//
//    companion object {
//        fun returnDemo() = BalanceDetails(
//            income = Random.nextDouble(100.0, 100000.0),
//            expenses = Random.nextDouble(100.0, 100000.0),
//            debtor = Random.nextDouble(100.0, 100000.0),
//            creditor = Random.nextDouble(100.0, 100000.0),
//            currency = AmCurrency.returnDemo(),
//        )
//    }
//
//}