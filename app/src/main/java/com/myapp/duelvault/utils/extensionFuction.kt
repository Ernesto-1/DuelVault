package com.myapp.duelvault.utils

import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

fun Long.toTime(): String {
    val now = System.currentTimeMillis()
    val diff = now - this

    val seconds = diff / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    val days = hours / 24

    return when {
        seconds < 60 -> "hace unos segundos"
        minutes < 60 -> "hace $minutes ${if (minutes == 1L) "minuto" else "minutos"}"
        hours < 24 -> "hace $hours ${if (hours == 1L) "hora" else "horas"}"
        days < 7 -> "hace $days ${if (days == 1L) "día" else "días"}"
        else -> {
            val date = Instant.ofEpochMilli(this)
                .atZone(ZoneId.systemDefault())
                .toLocalDate()
            val formatter = DateTimeFormatter.ofPattern("d/MMMM/yyyy", Locale.getDefault())
            "el ${date.format(formatter)}"
        }
    }
}