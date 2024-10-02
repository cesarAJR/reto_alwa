package com.cesar.reto_alwa.presentation.main3

import com.cesar.domain.model.App
import com.cesar.domain.model.Dashboard

sealed class Main3ScreenUiState {
    data class getData(val data: Dashboard): Main3ScreenUiState()
    object Nothing: Main3ScreenUiState()
}