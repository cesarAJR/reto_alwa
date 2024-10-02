package com.cesar.reto_alwa

import android.app.Activity
import android.app.AppOpsManager
import android.content.Intent
import android.os.Bundle
import android.os.Process
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.cesar.reto_alwa.navigation.MenuLateral
import com.cesar.reto_alwa.navigation.SetupNavGraph
import com.cesar.reto_alwa.navigation.TopBar
import com.cesar.reto_alwa.ui.theme.Reto_alwaTheme


class MainActivity3 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent()
    }

    private fun setContent(){
        setContent {
            Reto_alwaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }


}


@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )
    MenuLateral(navController = navController, drawerState = drawerState) {
        Contenido(navController = navController,drawerState=drawerState)
    }
}

@Composable
fun Contenido(navController: NavHostController,drawerState: DrawerState){
        Scaffold(
        topBar = {
             TopBar(drawerState = drawerState)
        }
        ) {padding->
            Box(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
            ) {
                SetupNavGraph(navController = navController)
            }
        }
}

