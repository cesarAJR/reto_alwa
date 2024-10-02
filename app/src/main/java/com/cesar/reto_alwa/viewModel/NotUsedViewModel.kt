package com.cesar.reto_alwa.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.domain.model.App
import com.cesar.domain.repository.IListRepository
import com.cesar.reto_alwa.presentation.main2.Main2ScreenElements
import com.cesar.reto_alwa.presentation.main2.Main2ScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class List2ViewModel(private val repository: IListRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<Main2ScreenUiState>(Main2ScreenUiState.Nothing)
    val uiState: StateFlow<Main2ScreenUiState> = _uiState

    var stateElements by mutableStateOf(Main2ScreenElements())

    fun updateList(list:MutableList<App>){
        stateElements= stateElements.copy(list = list)
    }

    fun getAppsNotUsed(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAppsNotUsed().collect{
                _uiState.value = Main2ScreenUiState.getData(it)
            }
        }
    }
}