package com.cesar.reto_alwa.viewModel


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cesar.domain.model.App
import com.cesar.domain.repository.IListRepository
import com.cesar.reto_alwa.presentation.moreUsed.MoreUsedScreenElements
import com.cesar.reto_alwa.presentation.moreUsed.MoreUsedScreenUiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MoreUsedViewModel(private val repository: IListRepository) : ViewModel() {
    private val _uiState = MutableStateFlow<MoreUsedScreenUiState>(MoreUsedScreenUiState.Nothing)
    val uiState: StateFlow<MoreUsedScreenUiState> = _uiState

    var stateElements by mutableStateOf(MoreUsedScreenElements())

    fun updateList(list:MutableList<App>){
        stateElements= stateElements.copy(list = list)
    }

  fun getAppsMoreUsed(){
      viewModelScope.launch(Dispatchers.IO) {
          repository.getAppsMoreUsed().collect{
              _uiState.value = MoreUsedScreenUiState.getData(it)
          }
      }
  }
}