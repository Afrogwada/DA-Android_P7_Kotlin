package com.openclassrooms.arista.data.repository

import android.util.Log
import com.openclassrooms.arista.data.dao.ExerciseDao
import com.openclassrooms.arista.data.dao.UserDao
import com.openclassrooms.arista.data.model.toDomain
import com.openclassrooms.arista.data.model.toEntity
import com.openclassrooms.arista.domain.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class UserRepository(
    private val userDao: UserDao
) {

    // Récupérer tous les utilisateurs en temps réel via un Flow
    fun getAllUsers(): Flow<List<User>> {
        return userDao.getAllUsers()
            .map { list -> list.map { it.toDomain() }}
            .catch { e ->
                Log.e("UserRepo", "Erreur getAllUsers", e)
                emit(emptyList()) // Ajout important pour éviter que l'UI ne reste bloquée
            }
    }

    // Récupère l'utilisateur. On prend le premier de la liste s'il existe.
    fun getUser(): Flow<User?> {
        return userDao.getAllUsers()
            .map { users -> users.firstOrNull()?.toDomain() }
            .catch { e ->
                Log.e("UserRepo", "Erreur getUser", e)
                emit(null)
            }
    }

    // Pour un utilisateur précis
    fun getUserById(id: Long): Flow<User?> {
        return userDao.getUserById(id)
            .map { it?.toDomain() }
            .catch { e ->
                Log.e("UserRepo", "Erreur getUserById", e)
                emit(null)
            }
    }

    // Ajouter ou mettre à jour un utilisateur
    suspend fun insertUser(user: User) {
        try {
            userDao.insertUser(user.toEntity())
        } catch (e: Exception) {
            Log.e("UserRepo", "Erreur insertUser", e)
        }
    }

    // Supprimer un utilisateur
    suspend fun deleteUser(user: User) {
        try {
            user.id?.let {
                userDao.deleteUserById(it)
            }
        } catch (e: Exception) {
            Log.e("UserRepo", "Erreur deleteUser", e)
        }
    }
}