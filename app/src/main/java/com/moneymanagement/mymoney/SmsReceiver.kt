package com.moneymanagement.mymoney

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import androidx.room.Room
import com.moneymanagement.mymoney.db.AppDB
import com.moneymanagement.mymoney.db.SMS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SmsReceiver : BroadcastReceiver() {

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    override fun onReceive(context: Context, intent: Intent) {
        val bundle: Bundle? = intent.extras
        if (bundle != null) {
            val db = Room.databaseBuilder(context, AppDB::class.java,"app_db").build()
            val pdus = bundle["pdus"] as Array<*>
            for (pdu in pdus) {
                val format = bundle.getString("format")
                val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray, format)
                val sender = smsMessage.displayOriginatingAddress
                val messageBody = smsMessage.messageBody
                val timestamp = smsMessage.timestampMillis
                println(messageBody)
                CoroutineScope(Dispatchers.IO).launch {
                    val smsDao = db.smsDao()
                    smsDao.insert(SMS(sender = sender, smsBody = messageBody, time = timestamp, isRead = false))
                }
            }
        }
    }
}