package com.openclassrooms.arista.domain.model

import java.time.LocalDateTime

data class Sleep(
    val id: Long? = null,
    @JvmField var startTime: LocalDateTime,
    var duration: Int,
    var quality: Int)
