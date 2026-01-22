package com.openclassrooms.arista.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.openclassrooms.arista.data.dao.ExerciseDao
import com.openclassrooms.arista.data.dao.SleepDao
import com.openclassrooms.arista.data.dao.UserDao
import com.openclassrooms.arista.data.entity.UserDto
import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.data.entity.ExerciseDto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset

@Database(entities = [UserDto::class, SleepDto::class, ExerciseDto::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun sleepDao(): SleepDao
    abstract fun exerciseDao(): ExerciseDao


    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.sleepDao(), database.userDao())
                }
            }
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null


        fun getDatabase(context: Context, coroutineScope: CoroutineScope): AppDatabase {
            //if the instance is not null return it, otherwise create a new database instance
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "AristaDB"
                )
                    .addCallback(AppDatabaseCallback(coroutineScope))
                    .build()
                INSTANCE = instance
                instance
            }
        }


        suspend fun populateDatabase(sleepDao: SleepDao, userDao: UserDao) {


            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(1).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 480, quality = 4
                )
            )
            sleepDao.insertSleep(
                SleepDto(
                    startTime = LocalDateTime.now().minusDays(2).atZone(ZoneOffset.UTC).toInstant()
                        .toEpochMilli(), duration = 450, quality = 3
                )
            )
        }
    }
}
