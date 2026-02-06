package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.SleepDao
import com.openclassrooms.arista.data.model.toDomain
import com.openclassrooms.arista.data.model.toEntity
import com.openclassrooms.arista.domain.model.Sleep
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SleepRepository(
    private val sleepDao: SleepDao
) {
    // Observer tous les enregistrements de sommeil en temps réel
    fun getAllSleeps(): Flow<List<Sleep>> {
        return sleepDao.getAllSleeps().map { list ->
            list.map { it.toDomain() }
        }
    }

    // Ajouter un enregistrement (suspendu pour les coroutines)
    suspend fun addSleep(sleep: Sleep) {
        sleepDao.insertSleep(sleep.toEntity())
    }

    // Supprimer un enregistrement
    suspend fun deleteSleep(sleep: Sleep) {
        // L'ID doit être ajouté au modèle Sleep
        sleep.id?.let {
            sleepDao.deleteSleepById(it)
        }
    }
}