package com.moneymanagement.mymoney.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.moneymanagement.mymoney.R
import com.moneymanagement.mymoney.db.Transaction
import com.moneymanagement.mymoney.db.Wallet
import com.moneymanagement.mymoney.db.toDateTimeString

@Composable
fun TransactionRow(transaction: Transaction,wallet: Wallet){
    Card(modifier = Modifier
        .fillMaxWidth()
        .background(
            color = MaterialTheme.colorScheme.surfaceBright,
            shape = RoundedCornerShape((10.dp))
        )
        ) {
        Row(modifier = Modifier.fillMaxWidth().padding(12.dp), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween){
            Icon(painter = painterResource(id = if(transaction.transactionType == "INCOME") R.drawable.baseline_add_circle_outline_24 else R.drawable.baseline_remove_circle_outline_24), contentDescription = "Add", tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(12.dp))
            Column (){
                Text(
                    text = "From : ${wallet.name}",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.W500,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )

                Text(
                    text = "For : ${transaction.expenseType}",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
                Text(
                    text = transaction.transactionTime.toDateTimeString(),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
            Text(
                text = if(transaction.transactionType == "INCOME") "+ $${transaction.transactionAmount}" else "- $${transaction.transactionAmount}", fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
