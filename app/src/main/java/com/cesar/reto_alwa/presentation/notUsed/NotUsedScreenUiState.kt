package com.cesar.reto_alwa.presentation.main2

import com.cesar.domain.model.App

sealed class Main2ScreenUiState {
    data class getData(val list: MutableList<App>?): Main2ScreenUiState()
    object Nothing: Main2ScreenUiState()
}