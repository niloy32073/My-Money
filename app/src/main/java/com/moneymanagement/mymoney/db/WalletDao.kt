package com.moneymanagement.mymoney.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WalletDao {
    @Insert
    suspend fun insert(wallet: Wallet)

    @Query("SELECT * FROM walletTable")
    suspend fun getWallets():List<Wallet>?
}