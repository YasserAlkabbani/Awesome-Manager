package com.awesome.manager.core.model

data class AmCurrency(
    val id: String,
    val countryName: String,
    val imageUrl: String,
    val currencyCode: String,
    val currencyName: String,
    val currencySymbol: String,
    val createdAt: String,
    val updatedAt: String
)

data class CurrencyWithBalance(
    val amCurrency: AmCurrency,
    val incoming:Double,
    val outgoing:Double,
    val borrow:Double,
    val lent:Double,
    val netIncoming: Double = incoming - outgoing,
    val netLent: Double = lent - borrow,
    val netCash:Double=(incoming+borrow)-(outgoing+lent)
)