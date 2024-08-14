package com.moneymanagement.mymoney.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.Transaction
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date

class StatisticsScreenViewModel(private val repository: Repository):ViewModel() {

    private val _currentMonthIncome= MutableStateFlow<List<Double>>(emptyList())
    val currentMonthIncome = _currentMonthIncome.asStateFlow()

    private val _currentMonthExpense= MutableStateFlow<List<Double>>(emptyList())
    val currentMonthExpense = _currentMonthExpense.asStateFlow()

    private val _expenseTypeAmount= MutableStateFlow<List<Double>>(emptyList())
    val expenseTypeAmount = _expenseTypeAmount.asStateFlow()

    fun fetchData(transactions: List<Transaction>){
        viewModelScope.launch {
            groupTransactionsByWeek(transactions)
            delay(200)
            getTotalAmountByExpenseType(transactions)
        }
    }

    fun groupTransactionsByWeek(transactions: List<Transaction>) {
        // Get the start and end dates of the current month
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        val startOfMonth = calendar.time
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        val endOfMonth = calendar.time

        // Define week periods
        val weeks = getWeeksInMonth(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH))

        // Initialize lists for totals with 4 weeks
        val incomeTotals = MutableList(weeks.size) { 0.0 }
        val expenseTotals = MutableList(weeks.size) { 0.0 }

        // Process transactions
        transactions.forEach { transaction ->
            val transactionDate = Date(transaction.transactionTime)
            weeks.forEachIndexed { index, week ->
                if (!transactionDate.before(week.first) && !transactionDate.after(week.second)) {
                    when (transaction.transactionType) {
                        "INCOME" -> incomeTotals[index] += transaction.transactionAmount
                        "EXPENSE" -> expenseTotals[index] += transaction.transactionAmount
                    }
                    return@forEach
                }
            }
        }

        // Use incomeTotals and expenseTotals as needed
        _currentMonthIncome.value = incomeTotals
        _currentMonthExpense.value = expenseTotals
    }

    fun getTotalAmountByExpenseType(transactions: List<Transaction>){
        // Define all possible expense types
        val expenseTypes = listOf("Health", "Food", "Education", "Transportation", "Accommodation", "Others")

        // Initialize a map with all expense types and default amounts set to 0.0
        val totalsByExpenseType = expenseTypes.associateWith { 0.0 }.toMutableMap()

        // Process each transaction
        transactions.forEach { transaction ->
            if (transaction.transactionType == "EXPENSE") {
                // Add the amount to the existing total for the given expenseType
                val currentTotal = totalsByExpenseType[transaction.expenseType] ?: 0.0
                totalsByExpenseType[transaction.expenseType] = currentTotal + transaction.transactionAmount
            }
        }

        // Convert the map values to a list of totals in the same order as the expenseTypes list
        _expenseTypeAmount.value = expenseTypes.map { totalsByExpenseType[it] ?: 0.0 }
    }
}

fun getWeeksInMonth(year: Int, month: Int): List<Pair<Date, Date>> {
    val weeks = mutableListOf<Pair<Date, Date>>()
    val calendar = Calendar.getInstance()
    calendar.set(year, month, 1)

    while (calendar.get(Calendar.MONTH) == month) {
        val weekStart = calendar.time

        calendar.add(Calendar.DAY_OF_MONTH, 6) // Move to end of the week
        if (calendar.get(Calendar.MONTH) != month) {
            calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
        }
        val weekEnd = calendar.time
        weeks.add(Pair(weekStart, weekEnd))

        calendar.add(Calendar.DAY_OF_MONTH, 1) // Move to the start of the next week
    }

    // Ensure there are exactly 4 weeks
    if (weeks.size < 4) {
        val lastWeek = weeks.last()
        val extraWeeks = 4 - weeks.size
        repeat(extraWeeks) {
            weeks.add(lastWeek)
        }
    }

    return weeks
}
