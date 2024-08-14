package com.moneymanagement.mymoney.db

import androidx.room.Entity
import androidx.room.PrimaryKey

//INCOME,EXPENSE


//Health,Food,Education,Transportation,Accommodation,Others,NA


@Entity(tableName = "transactionTable")
data class Transaction (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val transactionType:String,
    val transactionTime:Long,
    val transactionAmount:Double,
    val walletId:Int,
    val expenseType:String,
    val smsId:Int
)