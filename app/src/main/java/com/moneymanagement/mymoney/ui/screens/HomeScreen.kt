package com.moneymanagement.mymoney.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.navigation.BottomNavItems


@Composable
fun HomeScreen(navController: NavHostController){
    Text(text = "Home", modifier = Modifier.clickable { navController.navigate(BottomNavItems.Profile.route) })
}