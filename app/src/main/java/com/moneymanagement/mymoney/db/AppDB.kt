package com.moneymanagement.mymoney.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [User::class,SMS::class,Transaction::class,Wallet::class], version = 1)
abstract class AppDB : RoomDatabase(){
    abstract fun userDao():UserDao
    abstract fun smsDao():SMSDao
    abstract fun walletDao():WalletDao
    abstract fun transactionDao():TransactionDao
}