package com.moneymanagement.mymoney.ui.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignInViewmodel(private val repository: Repository):ViewModel() {
    private val _message= MutableStateFlow<String?>(null)
    val message = _message.asStateFlow()
    private val _isLogin= MutableStateFlow<Boolean>(false)
    val isLogin = _isLogin.asStateFlow()
    fun login(email:String,password:String){
        viewModelScope.launch {
            try {
                val loggInOutput = repository.checkUser(email)
                if(loggInOutput.id == null){
                    _message.value = "No user Exist"
                }
                else if(loggInOutput.password == password){
                    _message.value = "LogIn Successful"
                    _isLogin.value = true
                }
                else
                    _message.value = "Wrong Password"
            } catch (e:Exception){
                Log.d("Error",e.message.toString())
                _message.value = e.message.toString()
            }
        }
    }
}