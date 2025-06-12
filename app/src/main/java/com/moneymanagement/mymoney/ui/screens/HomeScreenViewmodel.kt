package com.moneymanagement.mymoney.ui.screens

import android.provider.Telephony.Sms
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.generationConfig
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.SMS
import com.moneymanagement.mymoney.db.Transaction
import com.moneymanagement.mymoney.db.Wallet
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.Calendar

data class Result(
    val transactionType: String,
    val transactionAmount: Double,
    val expenseType: String
)

data class ErrorDetail(
    val code: Int,
    val message: String,
    val status: String
)

sealed class ApiResponse {
    data class Transaction(
        val isTransaction: Boolean,
        val result: Result
    ) : ApiResponse()

    data class NonTransaction(
        val isTransaction: Boolean
    ) : ApiResponse()

    data class Error(
        val error: ErrorDetail
    ) : ApiResponse()
}

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

    val apiKey = "Your API Key"

    val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = apiKey,
        generationConfig = generationConfig {
            responseMimeType = "application/json"
        }
    )

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

    fun insertTransaction(transaction: Transaction){
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    fun updateSMS(sms: SMS){
        viewModelScope.launch {
            repository.updateSMS(sms)
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

    fun fetchData(){
        viewModelScope.launch {
            fetchWallet()
            delay(100)
            fetchCurrentMonthTransactions()
            delay(100)
            calculateTotalBalance()
            delay(100)
            calculateThisMonthIncome()
            delay(100)
            calculateThisMonthExpenses()
            delay(100)
            unReadSMS()
            delay(100)
            smsAnalysis()
            delay(100)
            recentTransactions()
        }
    }


    fun smsAnalysis(){
        viewModelScope.launch {
            println("SMS Analysing")
            unReadSMS.value.forEach { sms->
                val prompt ="""
                    I give you a sms. Read this sms. sender: ${sms.sender} and SMS body: ${sms.smsBody} .
                    transaction Type can be either INCOME or EXPENSE. if you find no transactionAmount than count that sms as non-transactional
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

                val output = generativeModel.generateContent(prompt)
                println(output)
                println(output.text)
                val response = """${output.text}"""
                val data = parseApiResponse(response)
                delay(1000)
                when(data){
                    is ApiResponse.Transaction->{
                        viewModelScope.launch {
                            insertTransaction(Transaction(transactionType = data.result.transactionType, smsId = sms.id, expenseType = data.result.expenseType, transactionTime = sms.time, transactionAmount = data.result.transactionAmount, walletId = 0))
                            updateSMS(SMS(id = sms.id, sender = sms.sender, smsBody = sms.smsBody, time = sms.time,isRead = true))
                            fetchCurrentMonthTransactions()
                            if(data.result.transactionType == "EXPENSE")
                                calculateThisMonthExpenses()
                            else{
                                calculateThisMonthIncome()
                            }
                            recentTransactions()
                        }
                    }
                    is ApiResponse.NonTransaction->{
                        updateSMS(SMS(id = sms.id, sender = sms.sender, smsBody = sms.smsBody, time = sms.time,isRead = true))
                        println("Not A Transaction")
                    }
                    else->{
                        println("SomeThing Wrong...")
                    }
                }
            }
        }
    }

    fun fetchWallet(){
        viewModelScope.launch {
            println("Wallet Fetching")
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
            println("CurrentMonth Transaction")
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
        println("Calculating Balance")
        var balance = 0.0
        wallets.value.forEach {
            balance += it.balance
        }
        _totalBalance.value=balance
    }
    fun calculateThisMonthExpenses(){
        println("Calculating this month Expense")
        var balance = 0.0
        currentMonthTransactions.value.forEach {
            if(it.transactionType=="EXPENSE"){
                balance += it.transactionAmount
            }

        }
        _thisMonthExpense.value=balance
    }
    fun calculateThisMonthIncome(){
        println("Calculating this month income")
        var balance = 0.0
        currentMonthTransactions.value.forEach {
            if(it.transactionType=="INCOME"){
                balance += it.transactionAmount
            }

        }
        _thisMonthIncome.value=balance
    }
    fun recentTransactions(){
        println("Recent Transactions")
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

fun parseApiResponse(jsonString: String): ApiResponse {
    val jsonObject = JSONObject(jsonString)
    val isTransaction = jsonObject.getBoolean("isTransaction")

    return if (isTransaction) {
        val resultObject = jsonObject.getJSONObject("result")
        val transactionType = resultObject.getString("transactionType")
        val transactionAmount = resultObject.getDouble("transactionAmount")
        val expenseType = resultObject.getString("expenseType")

        ApiResponse.Transaction(
            isTransaction = isTransaction,
            result = Result(
                transactionType = transactionType,
                transactionAmount = transactionAmount,
                expenseType = expenseType
            )
        )
    } else {
        ApiResponse.NonTransaction(isTransaction = isTransaction)
    }
}
