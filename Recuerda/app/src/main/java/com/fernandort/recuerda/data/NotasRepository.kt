package com.fernandort.recuerda.data

import com.fernandort.recuerda.data.room.notas.NotasDao
import javax.inject.Inject

class NotasRepository @Inject constructor(
    private val notasDao: NotasDao
) {

}