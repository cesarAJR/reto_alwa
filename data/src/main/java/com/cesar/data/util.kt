package com.cesar.data

import android.app.Activity
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import java.util.Calendar
import java.util.TimeZone

fun getListApps(context:Context):Map<String,String>{
    val packageManager: PackageManager = context.getPackageManager()
    val appInfos = packageManager.getInstalledApplications( PackageManager.GET_META_DATA )
    val appInfoMap = HashMap<String,String>()
    for ( appInfo in appInfos ) {
        appInfoMap[ appInfo.packageName ]= packageManager.getApplicationLabel( appInfo ).toString()
    }
    return appInfoMap
}

fun getListAppsStats(context:Context):Map<String, UsageStats>{
    val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    c.add(Calendar.MONTH, -1)
    val result: Long = c.getTimeInMillis()

    val now: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    val result1 = now.getTimeInMillis()

    val mUsageStatsManager = context.getSystemService(Activity.USAGE_STATS_SERVICE) as UsageStatsManager
    val listUsageStatsMap =
        mUsageStatsManager.queryAndAggregateUsageStats(result, result1)
    return listUsageStatsMap
}