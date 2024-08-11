package com.moneymanagement.mymoney.db

import androidx.room.Entity
import androidx.room.PrimaryKey

enum class TransactionType{
    INCOME,EXPENSE
}

enum class ExpenseType{
    Health,Food,Education,Transportation,Accommodation,Others,NA
}

@Entity(tableName = "transactionTable")
data class Transaction (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val transactionType:TransactionType,
    val transactionTime:Long,
    val transactionAmount:Double,
    val walletId:Int,
    val expenseType:ExpenseType,
    val smsId:Int
)