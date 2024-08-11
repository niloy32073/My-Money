package com.moneymanagement.mymoney.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface WalletDao {
    @Insert
    suspend fun insert(wallet: Wallet)

    @Update
    suspend fun update(wallet: Wallet)

    @Query("SELECT * FROM walletTable")
    suspend fun getWallets():List<Wallet>?
}