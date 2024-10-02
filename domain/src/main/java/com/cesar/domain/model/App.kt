package com.cesar.domain.model

data class App(
    val name:String,
    val packageName:String,
    val timeUsed:String,
    val timeUsedInLong:Long,
    val timeUsedInInt:Int?=null,
    val icon:String,
)

data class Dashboard(
    val listMoreUsed:MutableList<App>,
    val listNotUsed:MutableList<App>,
    val listDataMobile:MutableList<App>
)
