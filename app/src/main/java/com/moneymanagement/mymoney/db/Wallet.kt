package com.moneymanagement.mymoney.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "walletTable")
data class Wallet(
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val name:String,
    val lastDigits:String,
    val balance:Double
)