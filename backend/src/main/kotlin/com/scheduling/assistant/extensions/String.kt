package com.scheduling.assistant.extensions

import java.util.Date
import com.scheduling.assistant.utils.DateFormatter

/**
 * Converts 'yyyy-MM-ddTHH:mmZ' ISO String to Date type
 *
 * Usage: "2022-06-14T00:00Z".iso()
 */
fun String.iso(): Date {
    return DateFormatter.iso(this)
}

fun String.iso(timezone: String): Date {
    return DateFormatter.iso(this, timezone)
}

fun String.date(): Date {
    return DateFormatter.date(this)
}

fun String.date(timezone: String): Date {
    return DateFormatter.date(this, timezone)
}


/**
 * Converts 'hh:mm aa' time String to Date type
 *
 * Usage: "07:00 AM".time()
 */
fun String.time(): Date {
    return DateFormatter.time(this)
}

fun String.time(timezone: String): Date {
    return DateFormatter.time(this, timezone)
}