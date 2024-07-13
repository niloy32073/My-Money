package com.moneymanagement.mymoney.ui.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.moneymanagement.mymoney.navigation.BottomNavItems

@Composable
fun BottomNavigationBar(navController: NavHostController){
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val items = listOf<BottomNavItems>(
        BottomNavItems.Home,
        BottomNavItems.Profile,
        BottomNavItems.Statistics,
        BottomNavItems.Transaction
    )
    NavigationBar() {
        items.forEach { item->
            NavigationBarItem(selected = item.route == currentRoute, onClick = { navController.navigate(item.route) }, icon = { Icon(
                painter = painterResource(id = item.iconId),
                contentDescription = item.label,
                tint = MaterialTheme.colorScheme.primary
            ) })

        }
    }
}