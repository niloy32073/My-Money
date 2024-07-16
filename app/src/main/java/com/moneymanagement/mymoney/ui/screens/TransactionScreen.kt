package com.moneymanagement.mymoney.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.navigation.BottomNavItems
import com.moneymanagement.mymoney.ui.components.BottomNavigationBar

@Composable
fun TransactionScreen(navController: NavHostController){
    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) {
        Text(text = "Transaction", modifier = Modifier
            .padding(it)
            .clickable { navController.navigate(BottomNavItems.Profile.route) })
    }
}