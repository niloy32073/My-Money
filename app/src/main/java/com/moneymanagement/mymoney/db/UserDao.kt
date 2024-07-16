package com.moneymanagement.mymoney.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Query("SELECT * FROM userTable WHERE id =:id")
    suspend fun getUser(id:Int):User

    @Query("SELECT id,email,password FROM userTable WHERE email =:email")
    suspend fun checkUser(email:String):LoginOutput
}