package com.moneymanagement.mymoney.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface SMSDao {
    @Insert
    suspend fun insert(sms: SMS)

    @Update
    suspend fun update(sms: SMS)

    @Query("SELECT * FROM smsTable")
    suspend fun getAllSMS():List<SMS>?

    @Query("SELECT * FROM smsTable WHERE isRead =:isRead")
    suspend fun getTypeSMS(isRead:Boolean):List<SMS>?

    @Query("SELECT * FROM smsTable WHERE id =:id")
    suspend fun getSMSByID(id:Int):SMS

}