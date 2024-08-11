package com.moneymanagement.mymoney.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneymanagement.mymoney.db.Wallet

@Composable
fun WalletUI(wallet: Wallet){
    Card(modifier = Modifier
        .width(300.dp)
        .background(
            color = MaterialTheme.colorScheme.primaryContainer,
            shape = RoundedCornerShape(10.dp)
        )
        .border(
            width = 2.dp,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            shape = RoundedCornerShape(10.dp)
        )
        .padding(10.dp)) {
        Text(
            text = "Wallet Name : ${wallet.name}",
            fontSize = 15.sp,
            fontWeight = FontWeight.W500,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Total Balance",
                fontSize = 20.sp,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = "$${wallet.balance}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
        Spacer(modifier = Modifier.fillMaxWidth().height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(
                text = "Identifier No",
                fontSize = 12.sp,
                fontWeight = FontWeight.W500,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(
                text = wallet.lastDigits,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer
            )
        }
    }
}
