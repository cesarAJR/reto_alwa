package com.cesar.reto_alwa.navigation

sealed class Screen(val route:String) {
    object MoreUsedScreen: Screen("more_used_screen")
    object Main2: Screen("main2_screen")
    object Main3: Screen("main3_screen")
    object Main4: Screen("main4_screen")
}