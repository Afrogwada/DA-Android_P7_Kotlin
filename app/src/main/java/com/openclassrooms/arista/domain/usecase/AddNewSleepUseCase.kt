package com.openclassrooms.arista.domain.usecase

import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.domain.model.Sleep
import javax.inject.Inject

class AddNewSleepUseCase @Inject constructor(private val sleepRepository: SleepRepository) {
    suspend fun execute(sleep: Sleep) = sleepRepository.addSleep(sleep)
}