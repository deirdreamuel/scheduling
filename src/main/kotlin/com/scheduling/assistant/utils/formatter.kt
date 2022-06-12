package com.scheduling.assistant.utils

import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat

class Formatter {
    private val ISO_8061_UTC_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US);
    private val TIME_FORMAT = SimpleDateFormat("hh:mm aa", Locale.US);

    fun iso(date: Date): String {
        var dateText = ""

        try {
            dateText = ISO_8061_UTC_FORMAT.format(date)
        } catch(e: Exception) {
            println("error parsing iso date")
            println(e)
        }

        return dateText
    }

    fun iso(text: String): Date {
        var date: Date?
    
        try {
            date = ISO_8061_UTC_FORMAT.parse(text)
        } catch(e: Exception) {
            println("Error parsing time to iso format w/ input: $text")
            throw Exception("Error parsing time to iso format w/ input: $text")
        }

        return date
    }

    fun time(date: Date): String {
        var dateText = ""

        try {
            dateText = TIME_FORMAT.format(date)
        } catch(e: Exception) {
            println("Error formatting date to hh:mm aa")
            println(e)
        }

        return dateText
    }

    fun time(text: String): Date {
        var date: Date?

        try {
            date = TIME_FORMAT.parse(text)
        } catch(e: Exception) {
            println("Error parsing time w/ input: $text")
            throw Exception("Error parsing time w/ input: $text")
        }

        return date
    }
}