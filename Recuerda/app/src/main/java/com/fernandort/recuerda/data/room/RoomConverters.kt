package com.fernandort.recuerda.data.room

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

class RoomConverters {
    @TypeConverter
    fun fromTimestamp(valor: Long): LocalDateTime {
        return Instant.ofEpochMilli(valor).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    @TypeConverter
    fun toTimestamp(valor: LocalDateTime): Long {
        return valor.toEpochSecond(ZoneOffset.UTC)
    }
}