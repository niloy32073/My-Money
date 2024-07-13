package com.moneymanagement.mymoney.ui.components

import android.text.InputType
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation

@Composable
fun CustomEditText(title:String,modifier: Modifier,keyboardType: KeyboardType,onValueChanged:(String)->Unit){
    var value by remember {
        mutableStateOf("")
    }
    OutlinedTextField(
        value = value,
        label = { Text(text = title, color = MaterialTheme.colorScheme.outline)},
        modifier = modifier,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        visualTransformation = if(keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
        onValueChange = { it ->
        value = it
        onValueChanged(value)
    }
    )
}