package com.moneymanagement.mymoney.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Query("SELECT * FROM transactionTable")
    suspend fun getAllTransactions():List<Transaction>

    @Query("SELECT * FROM transactionTable WHERE transactionType =:transactionType")
    suspend fun getTypedTransactions(transactionType: TransactionType):List<Transaction>?
}