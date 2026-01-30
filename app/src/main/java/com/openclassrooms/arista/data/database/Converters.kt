package com.openclassrooms.arista.data.database
import androidx.room.TypeConverter
import com.openclassrooms.arista.domain.model.ExerciseCategory

class Converters {
    @TypeConverter
    fun fromCategory(value: ExerciseCategory): String {
        return value.name
    }

    @TypeConverter
    fun toCategory(value: String): ExerciseCategory {
        return ExerciseCategory.valueOf(value)
    }

}
