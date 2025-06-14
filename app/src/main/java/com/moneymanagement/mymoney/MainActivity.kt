package com.moneymanagement.mymoney

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.moneymanagement.mymoney.db.AppDB
import com.moneymanagement.mymoney.navigation.SetNavGraph
import com.moneymanagement.mymoney.ui.theme.MyMoneyTheme


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Request permissions
        requestPermissions()

        // Initialize database and repository
        val db = Room.databaseBuilder(applicationContext, AppDB::class.java, "app_db").build()
        val repository = Repository(db)

        // Set content
        setContent {
            val navController = rememberNavController()
            val context = LocalContext.current
            val store = UserStore(context)
            val id = store.getAccessToken.collectAsState(initial = 0)
            MyMoneyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val startDestination = if (id.value == 0) "signin" else "home"
                    SetNavGraph(
                        navController = navController,
                        startDestination = startDestination,
                        repository = repository,
                        paddingValues = innerPadding,
                        id = id.value
                    )
                }
            }
        }
    }

    private fun requestPermissions() {
        registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            // Permissions are always granted
        }.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.RECEIVE_SMS,
                Manifest.permission.READ_SMS
            )
        )
    }
}


