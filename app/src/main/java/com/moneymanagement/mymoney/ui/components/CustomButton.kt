package com.moneymanagement.mymoney.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun CustomButton(title:String,textColor: Color,containerColor: Color,modifier: Modifier,onClicked:()->Unit){
    OutlinedButton(onClick = onClicked, modifier = modifier, shape = RoundedCornerShape(4.dp), border = BorderStroke(width = 2.dp, color = containerColor)) {
        Text(text = title, color = textColor)
    }
}