package com.awesome.manager.core.model

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.absoluteValue


data class AmAccount(
    val accountID: String,
    val creatorUserID: String,
    val name: String,
    val imageUrl: String?,
    val defaultTransactionTypeID: String,
    val currencyID: String,
    val pending: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)

data class AmAccountWithDetails(
    val account: AmAccount,
    val currencyCode : String,
    val currencySymbol : String,
    private val income: Double,
    private val expenses: Double,
    private val debtor: Double,
    private val creditor: Double,
) {
    private fun Double.asFormatted() = NumberFormat.getNumberInstance(Locale.US).format(this)

    private val netIncome: Double = income - expenses
    private val netDebtor: Double = debtor - creditor
    private val input: Double = income + debtor
    private val output: Double = expenses + creditor
    private val netCash: Double = input - output


    val formattedIncome: String = income.asFormatted()
    val formattedExpenses: String = expenses.asFormatted()
    val formattedDebtor: String = debtor.asFormatted()
    val formattedCreditor: String = creditor.asFormatted()

    val formattedNetIncome: String = netIncome.absoluteValue.asFormatted()
    val formattedNetDebtor: String = netDebtor.absoluteValue.asFormatted()
    val formattedNetCash: String = netCash.absoluteValue.asFormatted()

    val isPositiveCash: Boolean = netCash >= 0
    val isPositiveIncome: Boolean = netIncome >= 0
    val isPositiveDebtor: Boolean = netDebtor >= 0

    companion object {
        fun createDemo(index: Int) = AmAccountWithDetails(
            account = AmAccount(
                accountID = index.toString(),
                creatorUserID = "USER_ID",
                name = "ACCOUNT $index",
                imageUrl = "",
                defaultTransactionTypeID = "",
                pending = listOf(true, false).random(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                currencyID = ""
            ),
            currencyCode = "currencyCode",
            currencySymbol = "currencySymbol",
            income = 100.0,
            expenses = 250.0,
            debtor = 400.0,
            creditor = 550.0,
        )
    }
}