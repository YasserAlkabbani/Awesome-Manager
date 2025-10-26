package com.awesome.manager.core.model

data class AmCurrency(
    val id: String,
    val code: String,
    val name: String,
    val symbol: String,
) {
    companion object {
        fun returnDemo() = AmCurrency(
            id ="123",
            code ="USD",
            name ="United State Dollar",
            symbol ="$",
        )
    }
//    val id: String = currencyCode
//
//    companion object {
    //        val supportedCountries=listOf("US", "EU", "TR", "SY")

    //        private val supportedLocals by lazy {
//            Locale.getAvailableLocales().filter { locale ->
//                Timber.d("TEST_AM LOCALE_FILTER $locale ${locale.displayName} ${locale.country} ${locale.displayCountry}")
//                locale.country in supportedCountries
//            }.filterNotNull().distinctBy { it.country }
//        }
//        val supportedCurrencies = listOf("SYP", "USD", "TRY","EUR")
//        private val currencies by lazy {
//            Currency.getAvailableCurrencies()
//                .filterNotNull()
//                .filter { it.currencyCode in supportedCurrencies }
//                .map { currency ->
//                    Timber.d("TEST_AM CURRENCY $currency")
//                    AmCurrency(
//                        currencyCode = currency.currencyCode,
//                        currencyName = currency.displayName,
//                        currencySymbol = currency.symbol,
//                    )
//                }
//        }

//        fun returnCurrencies() = currencies
//
//        fun returnCurrency(currencyID: String) = currencies.first { it.id == currencyID }

//    }
}