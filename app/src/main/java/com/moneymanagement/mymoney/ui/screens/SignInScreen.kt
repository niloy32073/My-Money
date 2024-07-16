package com.moneymanagement.mymoney.ui.screens

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.moneymanagement.mymoney.navigation.BottomNavItems
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.CustomEditText

@Composable
fun SignInScreen(navController: NavHostController,viewmodel: SignInViewmodel){
    val modifier = Modifier.fillMaxWidth(.9f)
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var emailError by remember {
        mutableStateOf<String?>(null)
    }
    var passwordError by remember {
        mutableStateOf<String?>(null)
    }
    val message by viewmodel.message.collectAsState()
    val isLogin by viewmodel.isLogin.collectAsState()
    if(isLogin)
        navController.navigate(BottomNavItems.Home.route)
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val context = LocalContext.current
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomEditText(
                title = "Email",
                modifier = modifier,
                keyboardType = KeyboardType.Email,
                onValueChanged = { value,error ->
                    email = value
                    emailError=error
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
                onValueChanged = { value,error ->
                    password = value
                    passwordError = error
                })
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            CustomButton(
                title = "Sign In",
                textColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth(.9f)
            ) {
                if(email.isValidEmail() && password.length>5){
                   viewmodel.login(email, password)
                   if(message!=null){
                       Toast.makeText(context,message,Toast.LENGTH_LONG).show()
                   }
                }
                else{
                    if(!email.isValidEmail())
                        emailError = "Enter Proper Email"
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
                text = "Don't Have An Account ? SignUp",
                modifier = Modifier
                    .fillMaxWidth(.9f)
                    .clickable {
                        navController.navigate("signup")
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