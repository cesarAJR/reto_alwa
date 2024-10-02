package com.cesar.reto_alwa.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.domain.model.Dashboard
import com.cesar.domain.repository.IListRepository
import com.cesar.reto_alwa.presentation.main3.Main3ScreenElements
import com.cesar.reto_alwa.presentation.main3.Main3ScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class List3ViewModel(private val repository: IListRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<Main3ScreenUiState>(Main3ScreenUiState.Nothing)
    val uiState: StateFlow<Main3ScreenUiState> = _uiState

    var stateElements by mutableStateOf(Main3ScreenElements())

    fun updateData(data:Dashboard){
        stateElements= stateElements.copy(data = data)
    }

    fun getDashboard(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getAppsDashboard().collect{
                _uiState.value = Main3ScreenUiState.getData(it)
            }
        }
    }
}