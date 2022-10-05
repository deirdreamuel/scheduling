package com.scheduling.assistant.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateFormatter {
    companion object {
        private val ISO_DATE_TIME_UTC_FORMAT = SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'", Locale.US);
        private val DATE_FORMAT = SimpleDateFormat("yyyy-MM-dd", Locale.US);
        private val TIME_FORMAT = SimpleDateFormat("hh:mm aa", Locale.US);

        fun iso(date: Date?, timezone: String = "UTC"): String {
            var dateText = ""

            try {
                ISO_DATE_TIME_UTC_FORMAT.timeZone = TimeZone.getTimeZone(timezone)
                dateText = ISO_DATE_TIME_UTC_FORMAT.format(date)
            } catch(e: Exception) {
                println("error parsing iso date")
                println(e)
            }

            return dateText
        }

        fun iso(text: String, timezone: String = "UTC"): Date {
            var date: Date?

            try {
                ISO_DATE_TIME_UTC_FORMAT.timeZone = TimeZone.getTimeZone(timezone)
                date = ISO_DATE_TIME_UTC_FORMAT.parse(text)
            } catch(e: Exception) {
                println("Error parsing time to iso format w/ input: $text")
                throw Exception("Error parsing time to iso format w/ input: $text")
            }

            return date
        }

        fun date(date: Date?, timezone: String = "UTC"): String {
            var dateText = ""

            try {
                DATE_FORMAT.timeZone = TimeZone.getTimeZone(timezone)
                dateText = DATE_FORMAT.format(date)
            } catch(e: Exception) {
                println("error parsing iso date")
                println(e)
            }

            return dateText
        }

        fun date(text: String, timezone: String = "UTC"): Date {
            var date: Date?

            try {
                DATE_FORMAT.timeZone = TimeZone.getTimeZone(timezone)
                date = DATE_FORMAT.parse(text)
            } catch(e: Exception) {
                println("Error parsing time to iso format w/ input: $text")
                throw Exception("Error parsing time to iso format w/ input: $text")
            }

            return date
        }

        fun time(date: Date, timezone: String = "UTC"): String {
            var dateText = ""

            try {
                TIME_FORMAT.timeZone = TimeZone.getTimeZone(timezone)
                dateText = TIME_FORMAT.format(date)
            } catch(e: Exception) {
                println("Error formatting date to hh:mm aa")
                println(e)
            }

            return dateText
        }

        fun time(text: String, timezone: String = "UTC"): Date {
            var date: Date?

            try {
                TIME_FORMAT.timeZone = TimeZone.getTimeZone(timezone)
                date = TIME_FORMAT.parse(text)
            } catch(e: Exception) {
                println("Error parsing time w/ input: $text")
                throw Exception("Error parsing time w/ input: $text")
            }

            return date
        }
    }
}