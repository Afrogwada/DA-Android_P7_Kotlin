package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import com.openclassrooms.arista.domain.usecase.AddNewSleepUseCase
import com.openclassrooms.arista.domain.usecase.DeleteSleepUseCase
import com.openclassrooms.arista.domain.usecase.GetAllSleepsUseCase
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

class SleepUseCasesTest {
    private val mockSleepRepository: SleepRepository = mock()
    private lateinit var getAllSleepsUseCase: GetAllSleepsUseCase
    private lateinit var addNewSleepUseCase: AddNewSleepUseCase
    private lateinit var deleteSleepUseCase: DeleteSleepUseCase

    @Before
    fun setup() {
        getAllSleepsUseCase = GetAllSleepsUseCase(mockSleepRepository)
        addNewSleepUseCase = AddNewSleepUseCase(mockSleepRepository)
        deleteSleepUseCase = DeleteSleepUseCase(mockSleepRepository)
    }

    @Test
    fun `getAllSleepsUseCase should return flow from repository`() = runTest {
        val fakeSleeps = listOf(Sleep(id = 1, startTime = LocalDateTime.now(), duration = 480, quality = 4))
        whenever(mockSleepRepository.getAllSleeps()).thenReturn(flowOf(fakeSleeps))

        val result = getAllSleepsUseCase.execute().first()

        assertEquals(fakeSleeps, result)
    }

    @Test
    fun `addNewSleepUseCase should call repository addSleep`() = runTest {
        val newSleep = Sleep(startTime = LocalDateTime.now(), duration = 420, quality = 3)
        addNewSleepUseCase.execute(newSleep)
        verify(mockSleepRepository).addSleep(newSleep)
    }

    @Test
    fun `deleteSleepUseCase should call repository deleteSleep`() = runTest {
        val sleepToDelete = Sleep(id = 1, startTime = LocalDateTime.now(), duration = 480, quality = 4)
        deleteSleepUseCase.execute(sleepToDelete)
        verify(mockSleepRepository).deleteSleep(sleepToDelete)
    }
}