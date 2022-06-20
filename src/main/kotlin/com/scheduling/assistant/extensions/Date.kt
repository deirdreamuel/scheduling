package com.scheduling.assistant.extensions

import com.scheduling.assistant.utils.DateFormatter
import java.util.*

fun Date.iso(): String {
    return DateFormatter.iso(this)
}

fun Date.time(): String {
    return DateFormatter.time(this)
}

fun Date.add(span: Int, days: Int): Date {
    val calendar = Calendar.getInstance()

    calendar.time = this
    calendar.add(span, days)

    return calendar.time
}

fun Date.add(date: Date): Date {
    return Date(this.time + date.time)
}