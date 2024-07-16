package com.moneymanagement.mymoney.db

import androidx.compose.ui.graphics.ImageBitmap
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "userTable", indices = [Index(value = ["email"], unique = true)])
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int =0,
    val name:String,
    val phone:String,
    val email:String,
    val password:String,
    val profilePicture:ByteArray?
)