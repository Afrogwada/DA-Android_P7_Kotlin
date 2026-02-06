package com.openclassrooms.arista.data.model

import com.openclassrooms.arista.data.entity.SleepDto
import com.openclassrooms.arista.domain.model.Sleep
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset


fun SleepDto.toDomain(): Sleep {
    return Sleep(
        id = this.id,
        startTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(startTime), ZoneOffset.UTC),
        duration = duration,
        quality = quality
    )
}

fun Sleep.toEntity(): SleepDto {
    return SleepDto(
        id = this.id ?: 0,
        startTime = startTime.atZone(ZoneOffset.UTC).toInstant().toEpochMilli(),
        duration = duration,
        quality = quality
    )
}