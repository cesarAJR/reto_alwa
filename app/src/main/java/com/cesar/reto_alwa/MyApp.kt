package com.cesar.reto_alwa

import android.app.Application
import com.cesar.reto_alwa.di.appModule
import com.example.data.di.dataModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(dataModules+ appModules)
        }
    }

}

val appModules = listOf(appModule)
val dataModules = listOf(dataModule)