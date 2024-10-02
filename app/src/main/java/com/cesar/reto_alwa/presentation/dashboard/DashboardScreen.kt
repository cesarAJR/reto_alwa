package com.cesar.reto_alwa.presentation.main3

import android.app.AppOpsManager
import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cesar.reto_alwa.checkPermission
import com.cesar.reto_alwa.component.ItemApp

import com.cesar.reto_alwa.viewModel.List3ViewModel

import org.koin.androidx.compose.koinViewModel

@Composable
fun Main3Screen (viewModel: List3ViewModel = koinViewModel()) {
    viewModel.stateElements
    val context = LocalContext.current
    LaunchedEffect( true){
        if ( checkPermission(context) == AppOpsManager.MODE_ALLOWED){
            viewModel.getDashboard()
        }
    }

    val resultPermisition = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (checkPermission(context) == AppOpsManager.MODE_ALLOWED) {
            viewModel.getDashboard()
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
            Column() {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(fontSize = 24.sp),
                        text = "Mas Usados"
                    )
                     Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        viewModel.stateElements.data?.listMoreUsed?.let { apps ->
                            items(apps.take(3)) { app ->
                                ItemApp(app,context)
                            }
                        }
                    }
                Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(fontSize = 24.sp),
                        text = "No usados"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        viewModel.stateElements.data?.listNotUsed?.let { apps ->
                            items(apps.take(3)) { app ->
                                ItemApp(app,context)
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        style = TextStyle(fontSize = 24.sp),
                        text = "Mas Datos Mobiles"
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        viewModel.stateElements.data?.listDataMobile?.let { apps ->
                            items(apps.take(3)) { app ->
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
                is Main3ScreenUiState.Nothing -> {}
                is Main3ScreenUiState.getData -> {
                    it.data?.let {
                        viewModel.updateData(it)
                    }
                }
            }
        }
    }

}