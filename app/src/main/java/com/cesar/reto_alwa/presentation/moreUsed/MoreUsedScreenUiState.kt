package com.cesar.reto_alwa.presentation.moreUsed

import com.cesar.domain.model.App

sealed class MoreUsedScreenUiState {
    data class getData(val list: MutableList<App>?): MoreUsedScreenUiState()
    object Nothing: MoreUsedScreenUiState()
}