package com.moneymanagement.mymoney.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.User
import kotlinx.coroutines.launch

class SignUpViewmodel(private val repository: Repository):ViewModel(){
    fun addUser(user: User){
        viewModelScope.launch {
            try {
                repository.insertUser(user)
            } catch (e:Exception){
                Log.d("Error",e.message.toString())
            }
        }
    }
}