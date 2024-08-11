package com.moneymanagement.mymoney.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.db.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileScreenViewmodel(private val repository: Repository,private val id:Int): ViewModel() {
    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser = _currentUser.asStateFlow()

    fun getUser(id:Int){
        viewModelScope.launch {
            val user = repository.getUser(id)
            _currentUser.value = user
        }
    }

    fun changePassword(newPassword:String){
        viewModelScope.launch {
            currentUser.value?.let { User(id= it.id, name = it.name, phone = it.phone, email = it.email, password = newPassword, profilePicture = it.profilePicture) }
                ?.let { repository.updateUser(it) }
        }
    }
}