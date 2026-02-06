package com.openclassrooms.arista.di

import android.content.Context
import com.openclassrooms.arista.data.dao.ExerciseDao
import com.openclassrooms.arista.data.dao.SleepDao
import com.openclassrooms.arista.data.dao.UserDao
import com.openclassrooms.arista.data.database.AppDatabase
import com.openclassrooms.arista.data.repository.ExerciseRepository
import com.openclassrooms.arista.data.repository.SleepRepository
import com.openclassrooms.arista.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module //Indique à Hilt que ce fichier contient des "recettes de fabrication".
@InstallIn(SingletonComponent::class) // Signifie que les outils créés ici seront les mêmes pour toute l'application (un seul exemplaire de la base de données).
class AppModule {

    // On fournit un "Scope" (une portée) pour les coroutines de la base de données.
    // Le SupervisorJob permet que si une tâche échoue, les autres continuent.
    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    // Pour créer la base de données, Room a besoin du "Context" (l'environnement Android).
    // Hilt nous fournit le @ApplicationContext automatiquement.
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
        scope: CoroutineScope
    ): AppDatabase {
        return AppDatabase.getDatabase(context, scope) // Utilise la méthode définie dans  AppDatabase
    }

    // Le Repository ne sait pas créer le DAO. On explique ici à Hilt comment le récupérer
    // en passant par l'instance de la base de données fournie juste au-dessus.
    @Provides
    fun provideExerciseDao(database: AppDatabase): ExerciseDao {
        return database.exerciseDao() //
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }


    @Provides
    fun provideSleepDao(appDatabase: AppDatabase): SleepDao {
        return appDatabase.sleepDao()
    }

    // Le repository demande maintenant un ExerciseDao dans son constructeur.
    // Hilt voit qu'il sait fabriquer un ExerciseDao (grâce à provideExerciseDao) et l'injecte ici.
    @Provides
    @Singleton
    fun provideExerciseRepository(exerciseDao: ExerciseDao): ExerciseRepository {
        return ExerciseRepository(exerciseDao)
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }


    @Provides
    @Singleton
    fun provideSleepRepository(sleepDao: SleepDao): SleepRepository {
        return SleepRepository(sleepDao)
    }

}