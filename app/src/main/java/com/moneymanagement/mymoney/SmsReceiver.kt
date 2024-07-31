package com.moneymanagement.mymoney

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log

class SmsReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val bundle: Bundle? = intent.extras
        try {
            if (bundle != null) {
                val pdus = bundle["pdus"] as Array<*>?
                if (pdus != null) {
                    for (pdu in pdus) {
                        val smsMessage = SmsMessage.createFromPdu(pdu as ByteArray)
                        val sender = smsMessage.displayOriginatingAddress
                        val messageBody = smsMessage.messageBody

                        Log.d("SmsReceiver", "Sender: $sender; Message: $messageBody")

                        // Process the SMS data here (e.g., send to analytics server or local database)
                        processSmsData(sender, messageBody)
                    }
                }
            }
        } catch (e: Exception) {
            Log.e("SmsReceiver", "Exception smsReceiver: ${e.message}")
        }
    }

    private fun processSmsData(sender: String, messageBody: String) {
        // Implement your SMS data processing logic here
    }
}