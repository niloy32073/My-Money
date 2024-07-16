package com.moneymanagement.mymoney.ui.components

import android.text.InputType
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CustomEditText(
    title: String,
    modifier: Modifier,
    keyboardType: KeyboardType,
    errorMessage: String? = null,
    onValueChanged: (String,String?) -> Unit
) {
    var error =errorMessage
    var value by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier, horizontalAlignment = AbsoluteAlignment.Left) {
        if (error != null) {
            Text(
                text = error!!,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.error,
                fontSize = 5.sp
            )
        }
        OutlinedTextField(
            value = value,
            label = { Text(text = title, color = MaterialTheme.colorScheme.outline) },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
            visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
            onValueChange = { it ->
                value = it
                error = null
                onValueChanged(value,error)
            }
        )
    }

}