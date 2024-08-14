package com.moneymanagement.mymoney

import com.moneymanagement.mymoney.db.AppDB
import com.moneymanagement.mymoney.db.LoginOutput
import com.moneymanagement.mymoney.db.SMS
import com.moneymanagement.mymoney.db.Transaction
import com.moneymanagement.mymoney.db.User
import com.moneymanagement.mymoney.db.Wallet

class Repository(private  val db: AppDB) {
    suspend fun insertUser(user: User)=db.userDao().insert(user)
    suspend fun updateUser(user: User)=db.userDao().updateUser(user)
    suspend fun getUser(id:Int):User=db.userDao().getUser(id)
    suspend fun checkUser(email:String):LoginOutput=db.userDao().checkUser(email)
    suspend fun insertSMS(sms: SMS)=db.smsDao().insert(sms)
    suspend fun updateSMS(sms: SMS)=db.smsDao().update(sms)
    suspend fun insertWallet(wallet: Wallet)=db.walletDao().insert(wallet)
    suspend fun getWallets():List<Wallet>?=db.walletDao().getWallets()
    suspend fun getWallet(id: Int):Wallet=db.walletDao().getWallet(id)
    suspend fun updateWallet(wallet: Wallet)=db.walletDao().update(wallet)
    suspend fun getTransactionsForCurrentMonth(start:Long,end:Long):List<Transaction>?=db.transactionDao().getTransactionsForCurrentMonth(start, end)
    suspend fun getTransactions():List<Transaction>?=db.transactionDao().getAllTransactions()
    suspend fun insertTransaction(transaction: Transaction)=db.transactionDao().insert(transaction)
    suspend fun updateTransaction(transaction: Transaction)=db.transactionDao().update(transaction)
    suspend fun getUnreadSMS(isRead:Boolean):List<SMS>? = db.smsDao().getTypeSMS(isRead)
}