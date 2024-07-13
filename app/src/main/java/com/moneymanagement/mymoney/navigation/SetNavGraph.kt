package com.moneymanagement.mymoney.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moneymanagement.mymoney.ui.screens.HomeScreen
import com.moneymanagement.mymoney.ui.screens.ProfileScreen
import com.moneymanagement.mymoney.ui.screens.SignInScreen
import com.moneymanagement.mymoney.ui.screens.SignUpScreen
import com.moneymanagement.mymoney.ui.screens.StatisticsScreen
import com.moneymanagement.mymoney.ui.screens.TransactionScreen

@Composable
fun SetNavGraph(navController: NavHostController,startDestination:String){
    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavItems.Home.route){
            HomeScreen(navController)
        }
        composable(BottomNavItems.Profile.route){
            ProfileScreen(navController)
        }
        composable(BottomNavItems.Transaction.route){
            TransactionScreen(navController)
        }
        composable(BottomNavItems.Statistics.route){
            StatisticsScreen(navController)
        }
        composable("signup"){
            SignUpScreen(navController)
        }
        composable("signin"){
            SignInScreen(navController)
        }
    }
}