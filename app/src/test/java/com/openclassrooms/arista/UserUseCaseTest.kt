package com.openclassrooms.arista

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import com.openclassrooms.arista.domain.usecase.GetUserUsecase

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

class UserUseCaseTest {
    private val mockUserRepository: UserRepository = mock()
    private lateinit var getUserUsecase: GetUserUsecase

    @Before
    fun setup() {
        getUserUsecase = GetUserUsecase(mockUserRepository)
    }

    @Test
    fun `getUserUsecase should return user from repository`() = runTest {
        val fakeUser = User(id = 1, name = "Test", email = "test@test.com", password = "123")
        whenever(mockUserRepository.getUser()).thenReturn(flowOf(fakeUser))

        val result = getUserUsecase.execute().first()

        assertEquals(fakeUser, result)
    }
}