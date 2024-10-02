package com.cesar.reto_alwa.presentation.main4

import android.app.AppOpsManager
import android.content.Intent
import android.provider.Settings
import android.widget.Button
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.cesar.domain.model.App
import com.cesar.reto_alwa.checkPermission
import com.cesar.reto_alwa.component.ItemApp
import com.cesar.reto_alwa.viewModel.List2ViewModel
import com.cesar.reto_alwa.viewModel.List4ViewModel
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main4Screen (viewModel: List4ViewModel = koinViewModel()) {
    viewModel.stateElements
    val context = LocalContext.current
    LaunchedEffect( true){
        if ( checkPermission(context) == AppOpsManager.MODE_ALLOWED){
            viewModel.getAppsMobileData()
        }
    }

    val resultPermisition = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (checkPermission(context)== AppOpsManager.MODE_ALLOWED) {
            viewModel.getAppsMobileData()
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
                is Main4ScreenUiState.Nothing -> {}
                is Main4ScreenUiState.getData -> {
                    it.list?.let {
                        viewModel.updateList(it)
                    }
                }
            }
        }
    }
}
