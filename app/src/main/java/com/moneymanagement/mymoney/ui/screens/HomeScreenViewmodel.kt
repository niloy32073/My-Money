package com.moneymanagement.mymoney.ui.screens

import android.provider.Telephony.Sms
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.SMS
import com.moneymanagement.mymoney.db.Transaction
import com.moneymanagement.mymoney.db.TransactionType
import com.moneymanagement.mymoney.db.Wallet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar

sealed class Output()

class HomeScreenViewmodel(private val repository: Repository):ViewModel() {
    private val _wallets= MutableStateFlow<List<Wallet>>(emptyList())
    val wallets = _wallets.asStateFlow()

    private val _unReadSMS = MutableStateFlow<List<SMS>>(emptyList())
    val unReadSMS = _unReadSMS.asStateFlow()

    private val _currentMonthTransactions= MutableStateFlow<List<Transaction>>(emptyList())
    val currentMonthTransactions = _currentMonthTransactions.asStateFlow()

    private val _recentTransactions= MutableStateFlow<List<Transaction>>(emptyList())
    val recentTransactions = _recentTransactions.asStateFlow()

    private val _totalBalance= MutableStateFlow<Double>(0.0)
    val totalBalance = _totalBalance.asStateFlow()

    private val _thisMonthExpense= MutableStateFlow<Double>(0.0)
    val thisMonthExpense = _thisMonthExpense.asStateFlow()

    private val _thisMonthIncome= MutableStateFlow<Double>(0.0)
    val thisMonthIncome = _thisMonthIncome.asStateFlow()

    private val _openBottomSheet= MutableStateFlow<Boolean>(false)
    val openBottomSheet = _openBottomSheet.asStateFlow()

    val apiKey = "1036704232592"

    val generativeModel = GenerativeModel(
        modelName = "gemini-pro",
        apiKey = apiKey,
        generationConfig = generationConfig {
            responseMimeType = "application/json"
        }
    )

    val sms = SMS(
        id = 0,
        sender = "",
        smsBody = "",
        time = 0,
        isRead = true
    )


    val prompt ="""
        I give you a sms. Read this sms. sender: ${sms.sender} and SMS body: ${sms.smsBody} .
        transaction Type can be either INCOME or EXPENSE.
        transactionAmount will be the amount that transaction. if it's an income than transactionAmount will be just the newly added money not the new balance.
        expenseType will be Health / Food / Education / Transportation / Accommodation / Others / NA(if transactionType is INCOME than expenseType will be NA) 
        if the sms is a transactional give this response using this JSON schema:
        Output = {
            "isTransaction": boolean,
            "result":{
                "transactionType":String,
                "transactionAmount":Double,
                "expenseType":String
            }
        }
        Return: Output
        if the sms is a not transactional give this response using this JSON schema:
        Output = {
            "isTransaction": "false",
        }
        Return: Output
    """.trimIndent()

    fun openBottomSheet(){
        _openBottomSheet.value = true
    }

    fun closeBottomSheet(){
        _openBottomSheet.value = false
    }

    fun insertWallet(wallet: Wallet){
        viewModelScope.launch {
            repository.insertWallet(wallet)
        }
    }

    fun unReadSMS(){
        viewModelScope.launch {
            val sms = repository.getUnreadSMS(isRead = false)
            if(sms != null){
                _unReadSMS.value = sms
            }
        }
    }

    fun smsAnalysis(){
        viewModelScope.launch {
            unReadSMS.value.forEach { sms->

            }
        }
    }

    fun fetchWallet(){
        viewModelScope.launch {
            val availableWallets = repository.getWallets()
            if(availableWallets != null){
                _wallets.value = availableWallets
            }
            else{
                _wallets.value = emptyList()
            }
        }
    }

    fun fetchCurrentMonthTransactions(){
        viewModelScope.launch {
            val (startOfMonth, endOfMonth) = getCurrentMonthTimestamps()
            val currentMonthTransactions = repository.getTransactionsForCurrentMonth(startOfMonth,endOfMonth)
            if(currentMonthTransactions != null){
                _currentMonthTransactions.value = currentMonthTransactions
            }
            else{
                _currentMonthTransactions.value = emptyList()
            }
        }
    }

    fun calculateTotalBalance(){
        var balance = 0.0
        wallets.value.forEach {
            balance += it.balance
        }
        _totalBalance.value=balance
    }
    fun calculateThisMonthExpenses(){
        var balance = 0.0
        currentMonthTransactions.value.forEach {
            if(it.transactionType==TransactionType.EXPENSE){
                balance += it.transactionAmount
            }

        }
        _thisMonthExpense.value=balance
    }
    fun calculateThisMonthIncome(){
        var balance = 0.0
        currentMonthTransactions.value.forEach {
            if(it.transactionType==TransactionType.INCOME){
                balance += it.transactionAmount
            }

        }
        _thisMonthIncome.value=balance
    }
    fun recentTransactions(){
        val latestTransactions = currentMonthTransactions.value.sortedByDescending { it.transactionTime }.take(10)
        _recentTransactions.value = latestTransactions
    }
}


fun getCurrentMonthTimestamps(): Pair<Long, Long> {
    val calendar = Calendar.getInstance()

    // Set to the first day of the current month
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.set(Calendar.HOUR_OF_DAY, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    calendar.set(Calendar.MILLISECOND, 0)
    val startOfMonth = calendar.timeInMillis

    // Set to the last day of the current month
    calendar.add(Calendar.MONTH, 1)
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    calendar.add(Calendar.DAY_OF_MONTH, -1)
    calendar.set(Calendar.HOUR_OF_DAY, 23)
    calendar.set(Calendar.MINUTE, 59)
    calendar.set(Calendar.SECOND, 59)
    calendar.set(Calendar.MILLISECOND, 999)
    val endOfMonth = calendar.timeInMillis

    return Pair(startOfMonth, endOfMonth)
}