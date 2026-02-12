package com.openclassrooms.arista.data.repository

import android.util.Log
import com.openclassrooms.arista.data.dao.ExerciseDao
import com.openclassrooms.arista.data.model.toDomain
import com.openclassrooms.arista.data.model.toEntity
import com.openclassrooms.arista.domain.model.Exercise
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class ExerciseRepository(
    private val exerciseDao: ExerciseDao
) {
    // 1. Lire les exercices : on transforme le Flow du DAO (DTO) en Flow de domaine
    fun getAllExercises(): Flow<List<Exercise>> {
        return exerciseDao.getAllExercises()
            .map { list -> list.map { it.toDomain() }}
            // Log l'erreur
            .catch { e ->
                Log.e("ExerciseRepo", "Erreur getAllExercises", e)
                emit(emptyList()) // Ajout important pour éviter que l'UI ne reste bloquée
            }
    }

    // 2. Ajouter : suspend permet d'attendre la fin de l'opération sans bloquer l'interface
    suspend fun addExercise(exercise: Exercise) {
        try {exerciseDao.insertExercise(exercise.toEntity())}
        catch (e: Exception) {Log.e("ExerciseRepo", "Erreur addExercise", e)}
    }

    // 3. Supprimer : on récupère l'ID du modèle de domaine
    suspend fun deleteExercise(exercise: Exercise) {
        try {exercise.id?.let { exerciseDao.deleteExerciseById(it) }}
        catch (e: Exception) {Log.e("ExerciseRepo", "Erreur deleteExercise", e)}
    }
}
