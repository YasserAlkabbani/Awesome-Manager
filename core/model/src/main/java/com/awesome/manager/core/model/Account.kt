package com.awesome.manager.core.model

data class AmAccount(
    val id: String,
    val creatorUserID: String,
    val name: String,
    val imageUrl: String,
    val defaultTransactionType: AmTransactionType,
    val balanceDetails: BalanceDetails,
    val pending: Boolean,
    val alreadyOnNetwork: Boolean,
    val updatePermission: Boolean,
    val createdAt: Long,
    val updatedAt: Long,
) {
    companion object {
        fun createDemo(index: Int) = AmAccount(
            id = index.toString(),
            creatorUserID = "USER_ID",
            name = "ACCOUNT $index",
            imageUrl = "",
            defaultTransactionType = AmTransactionType.entries.toTypedArray().random(),
            balanceDetails = BalanceDetails.createDemo(),
            pending = listOf(true, false).random(),
            alreadyOnNetwork = true,
            updatePermission = listOf(true, false).random(),
            createdAt = System.currentTimeMillis(),
            updatedAt = System.currentTimeMillis(),
        )
    }
}

data class UpsertAccount(
    val id: String,
    val creatorUserId: String,
    val name: String,
    val imageUrl: String,
    val currencyId: String,
    val defaultTransactionTypeID: String,
    val alreadyOnNetwork: Boolean,
) {
    fun isValid() = name.isNotBlank() && currencyId.isNotBlank()
}