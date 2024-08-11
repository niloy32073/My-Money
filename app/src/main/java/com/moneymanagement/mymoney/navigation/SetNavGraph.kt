package com.moneymanagement.mymoney.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.moneymanagement.mymoney.Repository
import com.moneymanagement.mymoney.ui.screens.HomeScreen
import com.moneymanagement.mymoney.ui.screens.HomeScreenViewmodel
import com.moneymanagement.mymoney.ui.screens.ProfileScreen
import com.moneymanagement.mymoney.ui.screens.ProfileScreenViewmodel
import com.moneymanagement.mymoney.ui.screens.SignInScreen
import com.moneymanagement.mymoney.ui.screens.SignInViewmodel
import com.moneymanagement.mymoney.ui.screens.SignUpScreen
import com.moneymanagement.mymoney.ui.screens.SignUpViewmodel
import com.moneymanagement.mymoney.ui.screens.StatisticsScreen
import com.moneymanagement.mymoney.ui.screens.TransactionScreen
import com.moneymanagement.mymoney.ui.screens.TransactionScreenViewmodel

@Composable
fun SetNavGraph(navController: NavHostController,startDestination:String,repository: Repository,paddingValues: PaddingValues,id:Int){
    val homeScreenViewmodel = HomeScreenViewmodel(repository)
    val transactionScreenViewmodel = TransactionScreenViewmodel(repository)
    val profileScreenViewmodel = ProfileScreenViewmodel(repository,id)
    NavHost(navController = navController, startDestination = startDestination) {
        composable(BottomNavItems.Home.route){
            HomeScreen(navController,paddingValues, homeScreenViewmodel)
        }
        composable(BottomNavItems.Profile.route){
            ProfileScreen(navController,profileScreenViewmodel)
        }
        composable(BottomNavItems.Transaction.route){
            TransactionScreen(navController, transactionScreenViewmodel = transactionScreenViewmodel, homeScreenViewmodel = homeScreenViewmodel)
        }
        composable(BottomNavItems.Statistics.route){
            StatisticsScreen(navController)
        }
        composable("signup"){
            SignUpScreen(navController,SignUpViewmodel(repository))
        }
        composable("signin"){
            SignInScreen(navController, SignInViewmodel(repository))
        }
    }
}