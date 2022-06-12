package com.scheduling.assistant.utils
import com.scheduling.assistant.models.WeeklySchedule
import com.scheduling.assistant.models.Interval


import java.util.Date
import java.util.Locale
import java.text.SimpleDateFormat



class Validator {
    val formatter: Formatter = Formatter()

    fun iso(date: String): Boolean {
        var success: Boolean = false

        try {
            formatter.iso(date)
            success = true
        } catch(e: Exception) {
            println("error parsing iso date")
            println(e)
        }

        return success
    }

    fun time(date: String): Boolean {
        var success: Boolean = false

        try {
            formatter.time(date)
            success = true
        } catch(e: Exception) {
            println("error parsing iso date")
            println(e)
        }

        return success
    }


    fun interval(interval: Interval, isTime: Boolean = true): Boolean {
        try {
            if (isTime) {
                // check if time is in correct format
                if (!time(interval.start) || !time(interval.end)) {
                    return false;
                }

                // check if start >= end
                if (formatter.time(interval.start).after(formatter.time(interval.end))) {
                    return false
                }
            } else {
                // check if time is in correct format
                if (!iso(interval.start) || !iso(interval.end)) {
                    return false;
                }

                // check if start >= end
                if (formatter.iso(interval.start).after(formatter.iso(interval.end))) {
                    return false
                }
            }
            
        } catch(e: Exception) {
            println("Error parsing interval")
            println(e)
        }

        return true
    }


    fun week(schedule: WeeklySchedule?): Boolean {
        val days = listOf(
            schedule?.monday,
            schedule?.tuesday,
            schedule?.wednesday,
            schedule?.thursday,
            schedule?.friday,
            schedule?.saturday,
            schedule?.sunday
        )

        try {
            for (day in days) {
                if (day != null && !interval(day)) {
                    return false
                }
            }
        } catch (e: Exception) {
            println("error parsing iso date")
            println(e)
        }

        return true
    }
}