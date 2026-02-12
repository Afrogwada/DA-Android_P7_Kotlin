package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.mock
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class GetAllExercisesUseCaseTest {
    private val mockExerciseRepository: ExerciseRepository = mock()
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase

    @Before
    fun setup() {
        getAllExercisesUseCase = GetAllExercisesUseCase(mockExerciseRepository)
    }

    @Test
    fun `execute should return exercises flow from repository`() = runTest {
        // GIVEN : On prépare une liste d'exercices factice
        val fakeExercises = listOf(
            Exercise(id = 1, category = ExerciseCategory.Running, duration = 30, startTime = LocalDateTime.now(), intensity = 3)
        )
        // On dit au mock de renvoyer cette liste dans un Flow
        whenever(mockExerciseRepository.getAllExercises()).thenReturn(flowOf(fakeExercises))

        // WHEN : On appelle la méthode execute
        val result = getAllExercisesUseCase.execute().first()

        // THEN : On vérifie que le résultat est identique à nos données factices
        assertEquals(fakeExercises, result)
        verify(mockExerciseRepository).getAllExercises() // On vérifie que la méthode a bien été appelée
    }
}