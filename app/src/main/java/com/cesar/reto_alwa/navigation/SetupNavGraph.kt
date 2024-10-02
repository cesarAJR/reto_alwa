package com.cesar.reto_alwa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.cesar.reto_alwa.presentation.moreUsed.Main1Screen
import com.cesar.reto_alwa.presentation.main2.Main2Screen
import com.cesar.reto_alwa.presentation.main3.Main3Screen
import com.cesar.reto_alwa.presentation.main4.Main4Screen

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main3.route
    ) {
        composable(
            route = Screen.MoreUsedScreen.route,
        ) {
            Main1Screen(
            )
        }

        composable(
            route = Screen.Main2.route,
        ) {
            Main2Screen(
            )
        }

        composable(
            route = Screen.Main3.route,
        ) {
            Main3Screen(
            )
        }

        composable(
            route = Screen.Main4.route,
        ) {
            Main4Screen(
            )
        }
    }
}