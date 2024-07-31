package com.moneymanagement.mymoney.db

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "smsTable")
data class SMS (
    @PrimaryKey(autoGenerate = true) val id:Int=0,
    val sender:String,
    val smsBody:String,
    val time:Long,
    val isRead:Boolean=false
)

fun Long.toDateString():String{
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
    return format.format(date)
}

fun Long.toDateTimeString():String{
    val date = Date(this)
    val format = SimpleDateFormat("dd/MM/yyyy HH:mm",Locale.getDefault())
    return format.format(date)
}
   

fun Date.toDateString():String{
    val format = SimpleDateFormat("dd/MM/yyyy",Locale.getDefault())
    return format.format(this)
}
