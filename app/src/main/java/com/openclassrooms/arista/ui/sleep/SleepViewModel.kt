package com.openclassrooms.arista.ui.sleep

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.Exercise

import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.AddNewSleepUseCase

import com.openclassrooms.arista.domain.usecase.DeleteSleepUseCase
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SleepViewModel @Inject constructor(
    private val getAllSleepsUseCase: GetAllSleepsUseCase,
    private val addNewSleepUseCase: AddNewSleepUseCase,
    private val deleteSleepUseCase: DeleteSleepUseCase
) : ViewModel() {

    private val _sleepsFlow = MutableStateFlow<List<Sleep>>(emptyList())
    val sleepsFlow: StateFlow<List<Sleep>> = _sleepsFlow.asStateFlow()

    init {
        loadAllSleeps()
    }

    fun deleteSleep(sleep: Sleep) {
        viewModelScope.launch {
            deleteSleepUseCase.execute(sleep)
            // Note : Pas besoin de rappeler loadAllSleeps() car on utilise Flow.collect() donc mise à jour en temps réel
        }
    }

    private fun loadAllSleeps() {
        viewModelScope.launch {
            // S'abonne au flux de la base de données
            getAllSleepsUseCase.execute().collect { sleeps ->
                _sleepsFlow.value = sleeps
            }
        }
    }

    // Utilisation de viewModelScope.launch pour l'ajout asynchrone
    fun addNewSleep(sleep: Sleep) {
        viewModelScope.launch {
            addNewSleepUseCase.execute(sleep)
        }
    }
}
