package com.moneymanagement.mymoney.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.Transaction
import com.moneymanagement.mymoney.db.Wallet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TransactionScreenViewmodel(private val repository: Repository):ViewModel(){
    private val _transactions= MutableStateFlow<List<Transaction>>(emptyList())
    val transactions = _transactions.asStateFlow()

    private val _openDialog= MutableStateFlow<Boolean>(false)
    val openDialog = _openDialog.asStateFlow()

    private val _selectedTransaction= MutableStateFlow<Transaction?>(null)
    val selectedTransaction = _selectedTransaction.asStateFlow()

    private val _selectedWallet= MutableStateFlow<Wallet?>(null)
    val selectedWallet = _selectedWallet.asStateFlow()

    fun fetchTransactions(){
        viewModelScope.launch {
            val transactions = repository.getTransactions()
            if(transactions != null){
                _transactions.value = transactions
            }
            else{
                _transactions.value = emptyList()
            }
        }
    }

    fun updateTransaction(transaction: Transaction){
        viewModelScope.launch {
            repository.updateTransaction(transaction = transaction)
        }
    }

    fun openDialogBox(transaction: Transaction,wallet: Wallet){
        _openDialog.value = true
        _selectedTransaction.value = transaction
        _selectedWallet.value = wallet
    }

    fun closeDialogBox(){
        _openDialog.value = false
        _selectedTransaction.value = null
        _selectedWallet.value = null
    }
}