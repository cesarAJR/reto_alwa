package com.cesar.reto_alwa

import android.app.Activity
import android.app.AppOpsManager
import android.app.usage.NetworkStats
import android.app.usage.NetworkStatsManager
import android.app.usage.UsageEvents
import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.os.Process
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.lifecycleScope
import com.cesar.reto_alwa.ui.theme.Reto_alwaTheme
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var foregroundAppPackageName : String? = null
        val currentTime = System.currentTimeMillis()
// The `queryEvents` method takes in the `beginTime` and `endTime` to retrieve the usage events.
// In our case, beginTime = currentTime - 10 minutes ( 1000 * 60 * 10 milliseconds )
// and endTime = currentTime

        val appOps = getSystemService(APP_OPS_SERVICE) as AppOpsManager
        val mode = appOps.checkOpNoThrow(
            AppOpsManager.OPSTR_GET_USAGE_STATS,
            Process.myUid(), packageName
        )

//        startActivityForResult( Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS), 121212)

        val usageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val usageEvents = usageStatsManager.queryEvents( currentTime - (1000*60*10) , currentTime )
        val usageEvent = UsageEvents.Event()
        while ( usageEvents.hasNextEvent() ) {
            usageEvents.getNextEvent( usageEvent )


            val minutes = usageEvent.timeStamp / 1000 / 60
            val seconds = usageEvent.timeStamp / 1000 % 60


            val formatter: DateFormat = SimpleDateFormat("HH:mm:ss", Locale.US)
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"))
            val text: String = formatter.format(Date(usageEvent.timeStamp))

//            Log.d( "appp1---" , "${usageEvent.packageName} ${usageEvent.eventType} ${text}}" )
        }

        val list = getNonSystemAppsList(this)
        list.forEach {
//           Log.d("appp2---","${it.key} - ${it.value}")
        }

        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_DEFAULT)
        val pkgAppsList: List<ResolveInfo> =
            getPackageManager().queryIntentActivities(mainIntent, 0)
        pkgAppsList.forEach {
            val name = packageManager.getApplicationLabel( it.activityInfo.applicationInfo ).toString()
//            Log.d("appp2", "${it.resolvePackageName}  ${name}")

        }

        val c: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        c.add(Calendar.MONTH, -1)
        val result: Long = c.getTimeInMillis()

        val now: Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
        val result1 = now.getTimeInMillis()


        val mUsageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
        val lUsageStatsMap: Map<String, UsageStats> =
            mUsageStatsManager.queryAndAggregateUsageStats(result, result1)
        val packageManager: PackageManager = getPackageManager()

        lUsageStatsMap.forEach {

            val minutes = it.value.totalTimeInForeground / 1000 / 60
            val seconds = it.value.totalTimeInForeground / 1000 % 60
            if (it.key in list.keys){
               val value = list[it.key]
                Log.d("appp1---","${it.key} ${value} - ${minutes}-${seconds}")
            }
        }

        lifecycleScope.launch {
            val networkStatsManager =  getSystemService(Context.NETWORK_STATS_SERVICE) as NetworkStatsManager

            list.forEach {it->
                val info = packageManager.getApplicationInfo(it.key, 0)
                val uid = info.uid
                val nwStatsWifi: NetworkStats = networkStatsManager.queryDetailsForUid(
                    ConnectivityManager.TYPE_MOBILE,
                    null,
                    result,
                    result1,
                    uid
                )

                var receivedWifi : Long = 0L
                var sentWifi : Long = 0L

                val bucketWifi = NetworkStats.Bucket()
                while (nwStatsWifi.hasNextBucket()) {
                    nwStatsWifi.getNextBucket(bucketWifi)
                    receivedWifi = receivedWifi + bucketWifi.rxBytes
                    sentWifi = sentWifi + bucketWifi.txBytes
                }

                Log.d("App-mobile", "${it.value}  ${receivedWifi+ sentWifi}")
            }
        }



        setContent {
            Reto_alwaTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
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

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Reto_alwaTheme {
        Greeting("Android")
    }
}

private fun getNonSystemAppsList(activity: Activity) : Map<String,String> {
    val packageManager: PackageManager = activity.getPackageManager()
    val appInfos = packageManager.getInstalledApplications( PackageManager.GET_META_DATA )
    val appInfoMap = HashMap<String,String>()
    for ( appInfo in appInfos ) {
//        if ( appInfo.flags != ApplicationInfo.FLAG_SYSTEM ) {
            appInfoMap[ appInfo.packageName ]= packageManager.getApplicationLabel( appInfo ).toString()
//        }
    }
    return appInfoMap
}