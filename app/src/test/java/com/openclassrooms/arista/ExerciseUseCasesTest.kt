package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.domain.model.Exercise
import com.openclassrooms.arista.domain.model.ExerciseCategory
import com.openclassrooms.arista.domain.usecase.GetAllExercisesUseCase
import com.openclassrooms.arista.domain.usecase.AddNewExerciseUseCase
import com.openclassrooms.arista.domain.usecase.DeleteExerciseUseCase
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.mockito.kotlin.mock
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.time.LocalDateTime

class ExerciseUseCasesTest {
    private val mockExerciseRepository: ExerciseRepository = mock()
    private lateinit var getAllExercisesUseCase: GetAllExercisesUseCase
    private lateinit var addNewExerciseUseCase: AddNewExerciseUseCase
    private lateinit var deleteExerciseUseCase: DeleteExerciseUseCase

    @Before
    fun setup() {
        getAllExercisesUseCase = GetAllExercisesUseCase(mockExerciseRepository)
        addNewExerciseUseCase = AddNewExerciseUseCase(mockExerciseRepository)
        deleteExerciseUseCase = DeleteExerciseUseCase(mockExerciseRepository)
    }

    @Test
    fun `getAllExercisesUseCase should return flow from repository`() = runTest {
        val fakeExercises = listOf(Exercise(id = 1, category = ExerciseCategory.Running, duration = 30, startTime = LocalDateTime.now(), intensity = 3))
        whenever(mockExerciseRepository.getAllExercises()).thenReturn(flowOf(fakeExercises))

        val result = getAllExercisesUseCase.execute().first()

        assertEquals(fakeExercises, result)
        verify(mockExerciseRepository).getAllExercises()
    }

    @Test
    fun `addNewExerciseUseCase should call repository addExercise`() = runTest {
        val exercise = Exercise(category = ExerciseCategory.Swimming, duration = 45, startTime = LocalDateTime.now(), intensity = 4)

        addNewExerciseUseCase.execute(exercise)

        verify(mockExerciseRepository).addExercise(exercise)
    }

    @Test
    fun `deleteExerciseUseCase should call repository deleteExercise`() = runTest {
        val exercise = Exercise(id = 1, category = ExerciseCategory.Walking, duration = 20, startTime = LocalDateTime.now(), intensity = 1)

        deleteExerciseUseCase.execute(exercise)

        verify(mockExerciseRepository).deleteExercise(exercise)
    }
}