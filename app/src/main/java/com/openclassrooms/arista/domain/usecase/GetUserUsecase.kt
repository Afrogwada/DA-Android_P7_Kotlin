package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.UserRepository
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetUserUsecase @Inject constructor(private val userRepository: UserRepository) {
    // On change le type de retour pour correspondre au Flow du Repository
    fun execute(): Flow<User?> {
        return userRepository.getUser()
    }
}