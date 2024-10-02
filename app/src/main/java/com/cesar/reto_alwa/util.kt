package com.cesar.reto_alwa

import android.app.Activity
import android.app.AppOpsManager
import android.content.Context
import android.os.Process
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


fun checkPermission(context:Context):Int{
    val appOps = context.getSystemService(Activity.APP_OPS_SERVICE) as AppOpsManager
    val mode = appOps.checkOpNoThrow(
        AppOpsManager.OPSTR_GET_USAGE_STATS,
        Process.myUid(), context.packageName
    )
    return mode
}



