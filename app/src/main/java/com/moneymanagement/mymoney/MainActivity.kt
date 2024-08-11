package com.moneymanagement.mymoney

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.google.ai.client.generativeai.BuildConfig
import com.moneymanagement.mymoney.db.AppDB
import com.moneymanagement.mymoney.navigation.BottomNavItems
import com.moneymanagement.mymoney.navigation.SetNavGraph
import com.moneymanagement.mymoney.ui.components.BottomNavigationBar
import com.moneymanagement.mymoney.ui.components.CustomButton
import com.moneymanagement.mymoney.ui.components.CustomEditText
import com.moneymanagement.mymoney.ui.theme.MyMoneyTheme
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val db = Room.databaseBuilder(applicationContext,AppDB::class.java,"app_db").build()
        val repository = Repository(db)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val store = UserStore(context)
            val id = store.getAccessToken.collectAsState(initial = 0)
            MyMoneyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                   val startDestination = if(id.value == 0) "signin" else "home"
                        SetNavGraph(navController = navController, startDestination = startDestination, repository = repository, paddingValues = innerPadding, id = id.value)
                    }
                }
            }
        }
    }
