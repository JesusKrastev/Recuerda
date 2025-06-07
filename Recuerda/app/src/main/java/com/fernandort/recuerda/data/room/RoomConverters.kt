package com.fernandort.recuerda.data.room

import androidx.room.TypeConverter
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZoneOffset

class RoomConverters {
    @TypeConverter
    fun toLocalDateTime(valor: Long): LocalDateTime {
        return Instant.ofEpochMilli(valor).atZone(ZoneId.systemDefault()).toLocalDateTime()
    }

    @TypeConverter
    fun toTimestamp(valor: LocalDateTime): Long {
        return valor.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun toLocalDate(valor: Long): LocalDate {
        return return LocalDate.ofEpochDay(valor)
    }

    @TypeConverter
    fun toTimestamp(valor: LocalDate): Long {
        return valor.toEpochDay()
    }

    @TypeConverter
    fun toLocalTime(valor: Long): LocalTime {
        return LocalTime.ofNanoOfDay(valor)
    }

    @TypeConverter
    fun toTimestamp(valor: LocalTime): Long {
        return valor.toNanoOfDay()
    }
}