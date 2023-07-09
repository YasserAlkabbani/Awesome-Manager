package com.awesome.manager.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.AccountEntityWithData
import kotlinx.coroutines.flow.Flow

@Dao
interface AccountDao {

    @Upsert
    suspend fun upsertAccount(accountEntity: List<AccountEntity>)

    @Upsert
    suspend fun upsertAccount(accountEntity: AccountEntity)

    @Transaction
    @Query ("SELECT * FROM accounts")
    fun returnAccounts():Flow<List<AccountEntityWithData>>

    @Query("SELECT * FROM accounts WHERE pending=1")
    fun returnPendingAccount():Flow<List<AccountEntity>>

    @Transaction
    @Query ("SELECT * FROM accounts WHERE account_id=:accountId")
    fun returnAccountById(accountId:String):Flow<AccountEntityWithData>

}