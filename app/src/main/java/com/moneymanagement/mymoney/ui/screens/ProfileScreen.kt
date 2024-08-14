package com.moneymanagement.mymoney.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.moneymanagement.mymoney.R
import com.moneymanagement.mymoney.navigation.BottomNavItems
import com.moneymanagement.mymoney.ui.components.BottomNavigationBar
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.CustomEditText
import com.preat.peekaboo.image.picker.toImageBitmap

@Composable
fun ProfileScreen(navController: NavHostController,profileScreenViewmodel: ProfileScreenViewmodel,id:Int){
    val user by profileScreenViewmodel.currentUser.collectAsState()
    var imageBitmap by remember {
        mutableStateOf<ImageBitmap?>(null)
    }
    var currentPassword by remember {
        mutableStateOf("")
    }
    var newPassword by remember {
        mutableStateOf("")
    }
    var retypedNewPassword by remember {
        mutableStateOf("")
    }
    var currentPasswordError by remember {
        mutableStateOf<String?>(null)
    }
    var newPasswordError by remember {
        mutableStateOf<String?>(null)
    }
    var retypedNewPasswordError by remember {
        mutableStateOf<String?>(null)
    }
    Scaffold(bottomBar = { BottomNavigationBar(navController = navController) }) {
        LaunchedEffect(true) {
            profileScreenViewmodel.getUser(id)
        }
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(it)
            .verticalScroll(state = rememberScrollState()), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceAround) {

            if (user != null && user?.profilePicture == null) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_supervised_user_circle_24),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape)

                )
            } else if(user != null && user?.profilePicture != null) {
                user?.profilePicture?.toImageBitmap()?.let { it1 ->
                    Image(bitmap = it1, contentDescription = "Profile Picture",modifier = Modifier
                        .size(200.dp)
                        .clip(shape = CircleShape))
                }
            }
            else{
                println("User Null")
            }
            Spacer(modifier = Modifier.height(16.dp))

            user?.name?.let { it1 -> Text(text = it1, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSurface) }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier
                .fillMaxWidth(.9f)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp)) {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "User Phone :", fontWeight = FontWeight.W500, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    user?.phone?.let { it1 -> Text(text = it1, fontWeight = FontWeight.W500, color = MaterialTheme.colorScheme.onPrimaryContainer) }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(16.dp))
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = "User Email :", fontWeight = FontWeight.W500, color = MaterialTheme.colorScheme.onPrimaryContainer)
                    user?.email?.let { it1 -> Text(text = it1, fontWeight = FontWeight.W500, color = MaterialTheme.colorScheme.onPrimaryContainer) }
                }

            }
            Spacer(modifier = Modifier.height(16.dp))
            Column(modifier = Modifier
                .fillMaxWidth(.9f)
                .background(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "Change Password", fontWeight = FontWeight.W500, color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.onPrimaryContainer)
                Spacer(modifier = Modifier.height(16.dp))
                CustomEditText(title = "Current Password", errorMessage = currentPasswordError,modifier = Modifier.fillMaxWidth(), keyboardType = KeyboardType.Password , onValueChanged = {value,error->
                    currentPassword=value
                    currentPasswordError = error
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomEditText(title = "New Password", errorMessage = newPasswordError,modifier = Modifier.fillMaxWidth(), keyboardType = KeyboardType.Password , onValueChanged = {value,error->
                    currentPassword=value
                    currentPasswordError = error
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomEditText(title = "Retype New Password", errorMessage = retypedNewPasswordError,modifier = Modifier.fillMaxWidth(), keyboardType = KeyboardType.Password , onValueChanged = {value,error->
                    currentPassword=value
                    currentPasswordError = error
                })
                Spacer(modifier = Modifier.height(16.dp))
                CustomButton(title = "Submit", textColor = MaterialTheme.colorScheme.primary,
                    containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth(.9f)) {
                    
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            CustomButton(title = "Sign Out", textColor = MaterialTheme.colorScheme.primary,
                containerColor = MaterialTheme.colorScheme.onPrimaryContainer,
                modifier = Modifier.fillMaxWidth(.9f)) {
                if(currentPassword == user?.password && retypedNewPassword == newPassword){
                    profileScreenViewmodel.changePassword(newPassword=newPassword)
                }
                else if(currentPassword != user?.password){
                    currentPasswordError="Password doesn't match"
                }
                else{
                    retypedNewPasswordError="New Password doesn't match"
                }
            }

        }
    }
}

@Preview
@Composable
fun PreviewProfile(){
    val navController = rememberNavController()
    //ProfileScreen(navController = navController)
}