package com.cesar.reto_alwa.presentation.moreUsed

import android.app.AppOpsManager
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.cesar.reto_alwa.checkPermission
import com.cesar.reto_alwa.component.ItemApp

import com.cesar.reto_alwa.viewModel.MoreUsedViewModel
import org.koin.androidx.compose.koinViewModel


@Composable
fun Main1Screen(viewModel: MoreUsedViewModel = koinViewModel()) {
     viewModel.stateElements
    val context = LocalContext.current
    LaunchedEffect( true){
        if ( checkPermission(context)== AppOpsManager.MODE_ALLOWED){
            viewModel.getAppsMoreUsed()
        }
    }

    val resultPermisition = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (checkPermission(context)== AppOpsManager.MODE_ALLOWED) {
            viewModel.getAppsMoreUsed()
        }
    }


    Surface(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (checkPermission(context) != AppOpsManager.MODE_ALLOWED){
            Box {
               Button(onClick = {
                   resultPermisition.launch(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
               }) {
                   Text(text = "Ir a Permisos")
               }
            }
        }else{
            Box() {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                ) {
                    viewModel.stateElements.list?.let { apps ->
                        items(apps) { app ->
                            ItemApp(app,context)
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.uiState.collect {
            when (it) {
                is MoreUsedScreenUiState.Nothing -> {}
                is MoreUsedScreenUiState.getData -> {
                    it.list?.let {
                        viewModel.updateList(it)
                    }
                }
            }
        }
    }
}

