package com.moneymanagement.mymoney.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.db.Transaction
import com.moneymanagement.mymoney.db.Wallet
import com.moneymanagement.mymoney.ui.components.BottomNavigationBar
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.DropdownMenuForWallet
import com.moneymanagement.mymoney.ui.components.DropdownMenuForExpense
import com.moneymanagement.mymoney.ui.components.EditableTransactionRow
import com.moneymanagement.mymoney.ui.components.TransactionRow
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionScreen(navController: NavHostController,transactionScreenViewmodel: TransactionScreenViewmodel,homeScreenViewmodel: HomeScreenViewmodel){
    transactionScreenViewmodel.fetchTransactions()
    val transactions by transactionScreenViewmodel.transactions.collectAsState()
    val wallets by homeScreenViewmodel.wallets.collectAsState()
    val openDialog by transactionScreenViewmodel.openDialog.collectAsState()
    val groupedTransactions = groupTransactionsByDate(transactions = transactions)

    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) {innerPadding->
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding), horizontalAlignment = Alignment.CenterHorizontally) {
            if(openDialog){
                EditDialog(transactionScreenViewmodel = transactionScreenViewmodel, wallets = wallets)
            }
            LazyColumn(modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(.9f)) {
                    groupedTransactions.forEach { (date, transactions) ->
                        stickyHeader {
                            Text(text = date)
                        }
                        items(transactions){transaction->
                            val wallet = wallets.firstOrNull { it.id == transaction.walletId }
                            if(transaction.walletId == 0){
                                EditableTransactionRow(transaction = transaction, wallet = wallet, transactionScreenViewmodel = transactionScreenViewmodel)
                            }
                            else{
                                TransactionRow(transaction = transaction, wallet = wallet!!)
                            }

                        }
                    }
            }
        }
    }
}

@Composable
fun EditDialog(transactionScreenViewmodel: TransactionScreenViewmodel,wallets:List<Wallet>){
    val selectedItem by transactionScreenViewmodel.selectedTransaction.collectAsState()
    var newWalletId by remember {
        mutableIntStateOf(0)
    }

    var newExpenseType by remember {
        mutableStateOf(selectedItem?.expenseType)
    }

    Dialog(onDismissRequest = {transactionScreenViewmodel.closeDialogBox() }) {
        Card {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.SpaceBetween, horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Modify Transaction",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(16.dp))
                DropdownMenuForWallet(label = "Wallet", itemList =wallets, onSelectedValueChange = {value-> newWalletId = value })
                Spacer(modifier = Modifier.height(16.dp))
                if(selectedItem?.transactionType   == "EXPENSE")
                {
                    DropdownMenuForExpense(label = "Expense Type", itemList = listOf("Food","Health","Others","Education","Accommodation","Transportation"), onSelectedValueChange = { value-> newExpenseType = value })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    CustomButton(title = "Cancel", textColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.width(100.dp)) {
                        transactionScreenViewmodel.closeDialogBox()
                    }

                    CustomButton(title = "Submit", textColor = MaterialTheme.colorScheme.primary,
                        containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.width(100.dp)) {
                        if(selectedItem != null){
                            transactionScreenViewmodel.updateTransaction(Transaction(id = selectedItem!!.id, transactionType = selectedItem!!.transactionType, transactionTime = selectedItem!!.transactionTime, transactionAmount = selectedItem!!.transactionAmount, walletId = newWalletId, expenseType = (if(newExpenseType != null) newExpenseType else selectedItem!!.expenseType)!!, smsId = selectedItem!!.smsId))
                            val wallet = wallets.firstOrNull{it.id == newWalletId}
                            if(wallet != null){
                                val newAmount = if (selectedItem?.transactionType   == "EXPENSE") wallet.balance - selectedItem!!.transactionAmount else wallet.balance + selectedItem!!.transactionAmount
                                transactionScreenViewmodel.updateWallet(Wallet(id = wallet.id, name = wallet.name, lastDigits = wallet.lastDigits, balance = newAmount))
                            }

                        }
                        transactionScreenViewmodel.closeDialogBox()
                    }
                }
            }
        }
    }

}

fun groupTransactionsByDate(transactions: List<Transaction>): Map<String, List<Transaction>> {
    val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val currentDate = Calendar.getInstance().time
    val currentDateStr = sdf.format(currentDate)

    val previousDate = Calendar.getInstance().apply { add(Calendar.DATE, -1) }.time
    val previousDateStr = sdf.format(previousDate)

    val groupedTransactions = transactions.groupBy { transaction ->
        val dateStr = sdf.format(Date(transaction.transactionTime))
        when (dateStr) {
            currentDateStr -> "Today"
            previousDateStr -> "Yesterday"
            else -> dateStr
        }
    }

    return groupedTransactions.toSortedMap(compareByDescending { it }) // Sort by date descending
}

fun getWalletNames(wallets: List<Wallet>): List<String> {
    return wallets.mapNotNull { it.name }
}


