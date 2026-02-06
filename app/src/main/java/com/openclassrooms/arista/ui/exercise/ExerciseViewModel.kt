package com.openclassrooms.arista.ui.exercise

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExerciseViewModel @Inject constructor(
    private val getAllExercisesUseCase: GetAllExercisesUseCase,
    private val addNewExerciseUseCase: AddNewExerciseUseCase,
    private val deleteExerciseUseCase: DeleteExerciseUseCase
) : ViewModel() {
    private val _exercisesFlow = MutableStateFlow<List<Exercise>>(emptyList())
    val exercisesFlow: StateFlow<List<Exercise>> = _exercisesFlow.asStateFlow()

    init {
        loadAllExercises()
    }

    // Utilisation de viewModelScope.launch pour appeler une fonction suspend
    fun deleteExercise(exercise: Exercise) {
        viewModelScope.launch {
            deleteExerciseUseCase.execute(exercise)
            // Note : Pas besoin de rappeler loadAllExercises() car on utilise Flow.collect() donc mise à jour en temps réel
        }
    }

    // On "collecte" le Flow pour recevoir les mises à jour en temps réel
    private fun loadAllExercises() {
        viewModelScope.launch {
            getAllExercisesUseCase.execute().collect { exercises ->
                _exercisesFlow.value = exercises
            }
        }
    }

    // Utilisation de viewModelScope.launch pour l'ajout asynchrone
    fun addNewExercise(exercise: Exercise) {
        viewModelScope.launch {
            addNewExerciseUseCase.execute(exercise)
        }
    }
}
