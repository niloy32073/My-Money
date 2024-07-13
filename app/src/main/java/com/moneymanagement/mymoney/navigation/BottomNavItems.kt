package com.moneymanagement.mymoney.navigation

import com.moneymanagement.mymoney.R

sealed class BottomNavItems(val route: String, val iconId:Int ,val label: String) {
    data object Home : BottomNavItems("home", R.drawable.baseline_home_filled_24,"Home")
    data object Transaction : BottomNavItems("transaction", R.drawable.baseline_currency_exchange_24,  "Transaction")
    data object Statistics : BottomNavItems("statistics", R.drawable.baseline_auto_graph_24, "Statistics")
    data object Profile : BottomNavItems("profile", R.drawable.baseline_person_24, "Profile")
}