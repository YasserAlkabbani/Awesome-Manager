package com.awesome.manager.core.model

import java.util.Currency
import java.util.Locale

data class AmCurrency(
    val countryName: String,
    val imageUrl: String,
    val currencyCode: String,
    val currencyName: String,
    val currencySymbol: String,
) {
    val id: String = currencyCode

    companion object {
        private val supportedLocals by lazy {
            Locale.getAvailableLocales().filter { locale ->
                when (locale.country) {
                    "us", "eu", "tr", "sy" -> true
                    else -> false
                }
            }.filterNotNull()
        }
        private val currencies by lazy {
            supportedLocals.mapNotNull { locale ->
                Currency.getInstance(locale)?.let { currency ->
                    AmCurrency(
                        countryName = locale.displayName,
                        imageUrl = "https://flagicons.lipis.dev/flags/4x3/${locale.country}.svg",
                        currencyCode = currency.currencyCode,
                        currencyName = currency.displayName,
                        currencySymbol = currency.symbol,
                    )
                }
            }
        }

        fun returnCurrencies() = currencies

        fun returnCurrency(currencyID: String) = currencies.first { it.id == currencyID }

    }
}