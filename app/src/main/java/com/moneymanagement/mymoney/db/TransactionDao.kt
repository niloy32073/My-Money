package com.moneymanagement.mymoney.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TransactionDao {
    @Insert
    suspend fun insert(transaction: Transaction)

    @Update
    suspend fun update(transaction: Transaction)

    @Query("SELECT * FROM transactionTable")
    suspend fun getAllTransactions():List<Transaction>?

    @Query("SELECT * FROM transactionTable WHERE transactionType =:transactionType")
    suspend fun getTypedTransactions(transactionType: String):List<Transaction>?

    @Query("SELECT * FROM transactionTable WHERE transactionTime BETWEEN :start AND :end")
    suspend fun getTransactionsForCurrentMonth(start: Long, end: Long): List<Transaction>?
}