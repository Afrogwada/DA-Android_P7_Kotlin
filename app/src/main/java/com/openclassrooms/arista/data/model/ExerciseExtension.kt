package com.openclassrooms.arista.data.model

import com.openclassrooms.arista.data.entity.ExerciseDto
import com.openclassrooms.arista.domain.model.Exercise
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

fun ExerciseDto.toDomain(): Exercise {
    return Exercise(
        startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneOffset.UTC),
        duration = duration,
        category = category,
        intensity = intensity
    )
}

fun Exercise.toEntity(): ExerciseDto {
    return ExerciseDto(
        startTime = startTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
        duration = duration,
        category = category,
        intensity = intensity
    )
}