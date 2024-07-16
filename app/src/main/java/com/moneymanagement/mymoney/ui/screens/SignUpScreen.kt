package com.moneymanagement.mymoney.ui.screens

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.R
import com.moneymanagement.mymoney.db.User
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.CustomEditText
import com.preat.peekaboo.image.picker.SelectionMode
import com.preat.peekaboo.image.picker.rememberImagePickerLauncher
import com.preat.peekaboo.image.picker.toImageBitmap

@Composable
fun SignUpScreen(navController: NavHostController,viewmodel: SignUpViewmodel) {
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

    var nameError by remember {
        mutableStateOf<String?>(null)
    }
    var emailError by remember {
        mutableStateOf<String?>(null)
    }
    var phoneError by remember {
        mutableStateOf<String?>(null)
    }
    var passwordError by remember {
        mutableStateOf<String?>(null)
    }
    var retypedPasswordError by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()
    val singleImagePicker = rememberImagePickerLauncher(
        selectionMode = SelectionMode.Single,
        scope = scope,
        onResult = { byteArrays ->
            byteArrays.firstOrNull()?.let { it ->
                imageByteArray = it
                imageBitmap = it.toImageBitmap()
            }
        }
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Box(
                modifier = Modifier
                    .height(200.dp)
                    .width(200.dp),
                contentAlignment = Alignment.Center
            ) {
                if (imageBitmap == null) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_supervised_user_circle_24),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(200.dp)
                            .clip(shape = CircleShape)

                    )
                } else {
                    Image(bitmap = imageBitmap!!, contentDescription = "Profile Picture",modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape))
                }

                Text(
                    text = "Add Photo", modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = RectangleShape
                        )
                        .padding(5.dp)
                        .clickable {
                            singleImagePicker.launch()
                        }, color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Name",
                modifier = modifier,
                keyboardType = KeyboardType.Text,
                errorMessage = nameError,
                onValueChanged = { value,error ->
                    name = value
                    nameError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Email",
                modifier = modifier,
                keyboardType = KeyboardType.Email,
                errorMessage = emailError,
                onValueChanged = { value,error ->
                    email = value
                    emailError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Phone",
                modifier = modifier,
                keyboardType = KeyboardType.Phone,
                errorMessage = phoneError,
                onValueChanged = { value,error ->
                    phone = value
                    phoneError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Password",
                modifier = modifier,
                keyboardType = KeyboardType.Password,
                errorMessage = passwordError,
                onValueChanged = { value,error ->
                    password = value
                    passwordError = error
                    })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Retype Password",
                modifier = modifier,
                keyboardType = KeyboardType.Text,
                errorMessage = retypedPasswordError,
                onValueChanged = { value ,error->
                    retypedPassword = value
                    retypedPasswordError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomButton(
                title = "Sign Up",
                textColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth(.9f)
            ) {
                if(name.length>1 && phone.isValidPhone() && email.isValidEmail() &&password.length>5 && password == retypedPassword){
                    viewmodel.addUser(User(name = name, phone = phone, email = email, password = password ,profilePicture = imageByteArray))
                }
                else{
                    if(name.length<=1)
                        nameError="Type Proper Name"
                    if(!phone.isValidPhone())
                        phoneError="Type Proper Phone Number"
                    if(!email.isValidEmail())
                        emailError ="Type Proper Email"
                    if(password != retypedPassword)
                        retypedPasswordError = "Type Same Password"
                    if(password.length<6)
                        passwordError = "Password must be at least 6 characters"
                }

            }
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            HorizontalDivider(modifier = Modifier.fillMaxWidth(.9f))
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            Text(
                text = "Have An Account ? SignIn",
                modifier = Modifier.fillMaxWidth(.9f)
                    .clickable {
                        navController.navigate("signin")
                    },
                textAlign = TextAlign.End,
                textDecoration = TextDecoration.Underline,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
        }

    }
}

fun CharSequence?.isValidEmail() = !isNullOrEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()
fun CharSequence?.isValidPhone() = !isNullOrEmpty() && Patterns.PHONE.matcher(this).matches()