package com.cesar.data.repository

import android.app.Activity
import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.util.Log
import com.cesar.data.getListApps
import com.cesar.data.getListAppsStats
import com.cesar.domain.model.App
import com.cesar.domain.model.Dashboard
import com.cesar.domain.repository.IListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import java.text.DecimalFormat
import java.util.Calendar
import java.util.TimeZone

class ListRepository(private val context: Context) : IListRepository {
    override fun getAppsMoreUsed(): Flow<MutableList<App>> = flow{
        val listApps : MutableList<App> =  mutableListOf()

        val appInfoMap = getListApps(context)
        val listUsageStatsMap = getListAppsStats(context)

        listUsageStatsMap.forEach {
            val minutes = it.value.totalTimeInForeground / 1000 / 60
            val seconds = it.value.totalTimeInForeground / 1000 % 60
            if (it.key in appInfoMap.keys){
                if (it.value.totalTimeInForeground.toInt() !=0){
                    val value = appInfoMap[it.key]
                    listApps.add(App(value!!,it.key,"$minutes m $seconds s",it.value.totalTimeInForeground,0,""))
                }
            }
        }

        listApps.sortByDescending { it.timeUsedInLong }

        emit(listApps)
    }

    override fun getAppsNotUsed(): Flow<MutableList<App>> = flow{
        val listApps : MutableList<App> =  mutableListOf()
        val appInfoMap = getListApps(context)
        val listUsageStatsMap = getListAppsStats(context)

        listUsageStatsMap.forEach {
           if (it.value.totalTimeInForeground.toInt() ==0){
               if (it.key in appInfoMap.keys){
                   val value = appInfoMap[it.key]
                   listApps.add(App(value!!,it.key,"",it.value.totalTimeInForeground,0,""))
               }
           }

        }

        listApps.sortBy { it.name }
        emit(listApps)
    }

    override suspend fun getAppsMobileData(): Flow<MutableList<App>> = flow{
        val listApps : MutableList<App> =  mutableListOf()
        val networkStatsManager =  context.getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager
        val appInfoMap = getListApps(context)
        val packageManager: PackageManager = context.getPackageManager()

        val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        c.add(Calendar.MONTH, -1)
        val result: Long = c.getTimeInMillis()

        val now: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val result1 = now.getTimeInMillis()

        appInfoMap.forEach {it->
            val info = packageManager.getApplicationInfo(it.key, 0)
            val uid = info.uid
            val nwStatsWifi: NetworkStats = networkStatsManager.queryDetailsForUid(
                ConnectivityManager.TYPE_MOBILE,
                null,
                result,
                result1,
                uid
            )

            var receivedWifi = 0
            var sentWifi = 0

            val bucketWifi = NetworkStats.Bucket()
            while (nwStatsWifi.hasNextBucket()) {
                nwStatsWifi.getNextBucket(bucketWifi)
                receivedWifi = receivedWifi + bucketWifi.rxBytes.toInt()
                sentWifi = sentWifi + bucketWifi.txBytes.toInt()
            }

            val total =  receivedWifi + sentWifi
            val totalGB = total.toDouble()/1000000


            listApps.add(App(it.value,it.key,"$totalGB MB",0L,total,""))
        }

        listApps.sortByDescending { it.timeUsedInInt }

        emit(listApps)
    }

    override suspend fun getAppsDashboard(): Flow<Dashboard> = flow{
        val listMoreUsed : MutableList<App> =  mutableListOf()
        val listNotUsed : MutableList<App> =  mutableListOf()
        var listDataMobile : MutableList<App> =  mutableListOf()

        val appInfoMap = getListApps(context)
        val listUsageStatsMap = getListAppsStats(context)

        listUsageStatsMap.forEach {
            val minutes = it.value.totalTimeInForeground / 1000 / 60
            val seconds = it.value.totalTimeInForeground / 1000 % 60
            if (it.key in appInfoMap.keys){
                if (it.value.totalTimeInForeground.toInt() !=0){
                    val value = appInfoMap[it.key]
                    listMoreUsed.add(App(value!!,it.key,"$minutes m $seconds s",it.value.totalTimeInForeground,0,""))
                }else{
                    val value = appInfoMap[it.key]
                    listNotUsed.add(App(value!!,it.key,"",it.value.totalTimeInForeground,0,""))
                }
            }
        }

        listMoreUsed.sortByDescending { it.timeUsedInLong }
        listNotUsed.sortBy { it.name }
        getAppsMobileData().collect{
            listDataMobile = it
        }

        emit(Dashboard(listMoreUsed,listNotUsed,listDataMobile))
    }
}