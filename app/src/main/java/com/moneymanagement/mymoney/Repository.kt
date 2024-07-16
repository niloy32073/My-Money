package com.moneymanagement.mymoney

import com.moneymanagement.mymoney.db.AppDB
import com.moneymanagement.mymoney.db.LoginOutput
import com.moneymanagement.mymoney.db.User

class Repository(private  val db: AppDB) {
    suspend fun insertUser(user: User)=db.userDao().insert(user)
    suspend fun getUser(id:Int):User=db.userDao().getUser(id)
    suspend fun checkUser(email:String):LoginOutput=db.userDao().checkUser(email)
}