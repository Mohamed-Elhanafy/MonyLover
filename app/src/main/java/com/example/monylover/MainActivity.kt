package com.example.monylover

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.monylover.ui.components.NavBar
import com.example.monylover.ui.screens.AddScreen
import com.example.monylover.ui.screens.Categories
import com.example.monylover.ui.screens.ExpanseScreen
import com.example.monylover.ui.screens.ReportsScreen
import com.example.monylover.ui.screens.SettingScreen
import com.example.monylover.ui.theme.MonyLoverTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @ExperimentalMaterial3Api
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
        setContent {
            MonyLoverTheme {

                val navController = rememberNavController()
                val backStackEntry by navController.currentBackStackEntryAsState()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }

                showBottomBar = when (backStackEntry?.destination?.route) {
                    "setting/categories" -> false
                    else -> true
                }

                Scaffold(
                    bottomBar = {
                        if (showBottomBar){
                            NavBar(backStackEntry = backStackEntry, navController = navController)
                        }
                    },
                )

                { innerPadding ->
                    NavHost(navController = navController, startDestination = "expanses") {
                        composable("expanses") {

                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                ExpanseScreen(navController = navController)
                            }
                        }
                        composable("reports") {

                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                ReportsScreen(navController = navController)
                            }
                        }
                        composable("add") {

                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                AddScreen(navController = navController , context = this@MainActivity)
                            }
                        }
                        composable("setting") {

                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                                color = MaterialTheme.colorScheme.background
                            ) {
                                SettingScreen(navController = navController)
                            }
                        }
                        composable("setting/categories") {
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(innerPadding),
                            ) {
                                Categories(navController = navController)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun GreetingPreview() {
    MonyLoverTheme {
        Greeting("Android")
    }
}