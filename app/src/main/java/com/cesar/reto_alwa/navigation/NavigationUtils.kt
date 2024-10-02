package com.cesar.reto_alwa.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun currentRule(navController: NavHostController):String?=navController.currentBackStackEntryAsState().value?.destination?.route