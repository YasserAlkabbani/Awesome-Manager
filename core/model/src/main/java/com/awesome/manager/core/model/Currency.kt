package com.awesome.manager.core.model

data class AmCurrency(
    val id: String,
    val countryName: String,
    val imageUrl: String,
    val currencyCode: String,
    val currencyName: String,
    val currencySymbol: String,
    val createdAt: Long,
    val updatedAt: Long
) {
    companion object {
        fun createDemo() = AmCurrency(
            id = "CURRENCY",
            countryName = "USA",
            imageUrl = "",
            currencyCode = "USD",
            currencyName = "DOLLAR",
            currencySymbol = "$",
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
        )
    }
}