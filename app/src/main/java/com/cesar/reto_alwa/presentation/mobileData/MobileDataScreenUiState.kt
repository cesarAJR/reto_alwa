package com.cesar.reto_alwa.presentation.main4

import com.cesar.domain.model.App

sealed class Main4ScreenUiState {
    data class getData(val list: MutableList<App>?): Main4ScreenUiState()
    object Nothing: Main4ScreenUiState()
}