package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.awesome.manager.core.database.model.AccountEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Upsert
    suspend fun upsertAccount(vararg accountEntity: AccountEntity)

    @Query ("SELECT * FROM accounts")
    fun returnAccounts():Flow<List<AccountEntity>>

}