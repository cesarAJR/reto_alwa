package com.cesar.reto_alwa.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch

@Composable
fun MenuLateral(
    navController: NavHostController,
    drawerState: DrawerState,
    layout : @Composable () -> Unit
){
    val scope = rememberCoroutineScope()
    val items = listOf(
        NavigationItems(
            title = "Dashboard",
            route = Screen.Main3.route
        ),
        NavigationItems(
            title = "Mas tiempo de Uso",
            route = Screen.MoreUsedScreen.route
        ),
        NavigationItems(
            title = "No usadas",
            route = Screen.Main2.route
        ),
        NavigationItems(
            title = "Datos mobiles",
            route = Screen.Main4.route
        ),

    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                items.forEach {item->
                    NavigationDrawerItem(
                        label = { Text(text = item.title)},
                        selected = currentRule(navController) == item.route,
                        onClick = {
                            scope.launch {
                                drawerState.close()
                            }
                          navController.navigate(item.route!!)
                        }
                    )
                }
            }
        }
    ) {
        layout()
    }
}