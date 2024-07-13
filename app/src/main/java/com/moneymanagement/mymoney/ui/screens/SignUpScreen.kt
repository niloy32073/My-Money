package com.moneymanagement.mymoney.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.R
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.CustomEditText
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap

@Composable
fun SignUpScreen(navController: NavHostController){
    val modifier = Modifier.fillMaxWidth(.9f)
    var name by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var phone by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var retypedPassword by remember {
        mutableStateOf("")
    }
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    var imageByteArray by remember {
        mutableStateOf<ByteArray?>(null)
    }
    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let {it->
                imageByteArray = it
                imageBitmap = it.toImageBitmap()
            }
        }
    )

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Box (modifier = Modifier
            .height(200.dp)
            .width(200.dp),
            contentAlignment = Alignment.Center){
            if(imageBitmap == null){
                Icon(painter = painterResource(id = R.drawable.baseline_supervised_user_circle_24), contentDescription = "Profile", modifier = Modifier
                    .height(200.dp)
                    .width(200.dp))
            }
            else{
                Image(bitmap = imageBitmap!!, contentDescription = "Profile Picture")
            }

            Text(text = "Add Photo", modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.tertiaryContainer,
                    shape = RectangleShape
                )
                .padding(5.dp)
                .clickable {
                    singleImagePicker.launch()
                }, color = MaterialTheme.colorScheme.onTertiaryContainer)
        }
        CustomEditText(title = "Name", modifier = modifier, keyboardType = KeyboardType.Text, onValueChanged = {value->name=value})
        CustomEditText(title = "Email", modifier = modifier, keyboardType = KeyboardType.Email, onValueChanged = {value->email=value})
        CustomEditText(title = "Phone", modifier = modifier, keyboardType = KeyboardType.Phone, onValueChanged = {value->phone=value})
        CustomEditText(title = "Password", modifier = modifier, keyboardType = KeyboardType.Password, onValueChanged = {value->password=value})
        CustomEditText(title = "Retype Password", modifier = modifier, keyboardType = KeyboardType.Text, onValueChanged = {value->retypedPassword=value})
        CustomButton(title = "Sign Up", textColor = MaterialTheme.colorScheme.primary, containerColor = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.fillMaxWidth(.9f)) {

        }
    }
}