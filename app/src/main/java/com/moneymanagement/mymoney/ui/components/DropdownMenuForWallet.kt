package com.moneymanagement.mymoney.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.moneymanagement.mymoney.db.Wallet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownMenuForWallet(label: String, itemList: List<Wallet>,onSelectedValueChange:(Int) -> Unit) {
    var isExpanded by remember {
        mutableStateOf(false)
    }

    var value by remember {
        mutableStateOf("")
    }

    ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = { isExpanded = it }) {
        OutlinedTextField(
            value = value,
            onValueChange = {},
            label = { Text(text = label) },
            readOnly = true,
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth(.9f)
        )
        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = { isExpanded = false }) {
            itemList.forEach { item ->
                DropdownMenuItem(text = { Text(text = item.name) }, onClick = {
                    value = item.name
                    isExpanded = false
                    onSelectedValueChange(item.id)
                })
            }
        }
    }
}