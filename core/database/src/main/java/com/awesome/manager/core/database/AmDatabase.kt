package com.awesome.manager.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.awesome.manager.core.database.dao.AccountDao
import com.awesome.manager.core.database.dao.CurrencyDao
import com.awesome.manager.core.database.dao.TransactionDao
import com.awesome.manager.core.database.dao.TransactionTypeDao
import com.awesome.manager.core.database.dao.UserDao
import com.awesome.manager.core.database.model.AccountEntity
import com.awesome.manager.core.database.model.CurrencyEntity
import com.awesome.manager.core.database.model.TransactionEntity
import com.awesome.manager.core.database.model.TransactionTypeEntity
import com.awesome.manager.core.database.model.UserEntity


@Database(
    entities = [
        UserEntity::class,
        AccountEntity::class,
        TransactionEntity::class,
        TransactionTypeEntity::class,
        CurrencyEntity::class
    ],
    version = 1, exportSchema = false
)
abstract class AmDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun accountDao(): AccountDao
    abstract fun transactionDao(): TransactionDao
    abstract fun transactionTypeDao(): TransactionTypeDao
    abstract fun currencyDao(): CurrencyDao

}