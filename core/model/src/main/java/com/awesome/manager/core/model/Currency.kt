package com.awesome.manager.core.model

import timber.log.Timber
import java.util.Currency
import java.util.Locale

data class AmCurrency(
    val currencyCode: String,
    val currencyName: String,
    val currencySymbol: String,
) {
    val id: String = currencyCode

    companion object {
        //        val supportedCountries=listOf("US", "EU", "TR", "SY")

        //        private val supportedLocals by lazy {
//            Locale.getAvailableLocales().filter { locale ->
//                Timber.d("TEST_AM LOCALE_FILTER $locale ${locale.displayName} ${locale.country} ${locale.displayCountry}")
//                locale.country in supportedCountries
//            }.filterNotNull().distinctBy { it.country }
//        }
        val supportedCurrencies = listOf("SYP", "USD", "TRY","EUR")
        private val currencies by lazy {
            Currency.getAvailableCurrencies()
                .filterNotNull()
                .filter { it.currencyCode in supportedCurrencies }
                .map { currency ->
                    Timber.d("TEST_AM CURRENCY $currency")
                    AmCurrency(
                        currencyCode = currency.currencyCode,
                        currencyName = currency.displayName,
                        currencySymbol = currency.symbol,
                    )
                }
        }

        fun returnCurrencies() = currencies

        fun returnCurrency(currencyID: String) = currencies.first { it.id == currencyID }

    }
}