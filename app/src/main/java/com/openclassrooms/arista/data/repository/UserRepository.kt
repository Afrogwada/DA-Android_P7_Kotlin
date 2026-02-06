package com.openclassrooms.arista.data.repository

import com.openclassrooms.arista.data.dao.ExerciseDao
import com.openclassrooms.arista.data.dao.UserDao
import com.openclassrooms.arista.data.model.toDomain
import com.openclassrooms.arista.data.model.toEntity
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserRepository(
    private val userDao: UserDao
) {

    // Récupérer tous les utilisateurs en temps réel via un Flow
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers().map { list ->
            list.map { it.toDomain() }
        }
    }

    // Récupère l'utilisateur. On prend le premier de la liste s'il existe.
    fun getUser(): Flow<User?> {
        return userDao.getAllUsers().map { users ->
            users.firstOrNull()?.toDomain()
        }
    }

    // Pour un utilisateur précis
    fun getUserById(id: Long): Flow<User?> {
        return userDao.getUserById(id).map { it?.toDomain() }
    }

    // Ajouter ou mettre à jour un utilisateur
    suspend fun insertUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    // Supprimer un utilisateur
    suspend fun deleteUser(user: User) {
        user.id?.let {
            userDao.deleteUserById(it)
        }
    }
}