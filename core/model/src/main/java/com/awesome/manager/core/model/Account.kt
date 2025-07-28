package com.awesome.manager.core.model


data class AmAccount(
    val accountID: String,
    val creatorUserID: String,
    val name: String,
    val imageUrl: String,
    val defaultTransactionType: AmTransactionType,
    val currency: AmCurrency,
    val pending: Boolean,
    val alreadyOnNetwork: Boolean,
    val updatePermission: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
)

data class AmAccountWithBalance(
    val account: AmAccount,
    val balanceDetails: BalanceDetails,
) {
    companion object {
        fun createDemo(index: Int) = AmAccountWithBalance(
            account = AmAccount(
                accountID = index.toString(),
                creatorUserID = "USER_ID",
                name = "ACCOUNT $index",
                imageUrl = "",
                defaultTransactionType = AmTransactionType.entries.toTypedArray().random(),
                pending = listOf(true, false).random(),
                alreadyOnNetwork = true,
                updatePermission = listOf(true, false).random(),
                createdAt = System.currentTimeMillis(),
                updatedAt = System.currentTimeMillis(),
                currency = AmCurrency.returnCurrencies().first()
            ),
            balanceDetails = BalanceDetails.createDemo(),
        )
    }
}