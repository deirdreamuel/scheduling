package com.scheduling.assistant.utils
import com.scheduling.assistant.models.WeeklySchedule
import com.scheduling.assistant.models.Interval


class DateValidator {
    fun iso(date: String): Boolean {
        var success: Boolean = false

        try {
            DateFormatter.iso(date)
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
            DateFormatter.time(date)
            success = true
        } catch(e: Exception) {
            println("error parsing iso date")
            println(e)
        }

        return success
    }


    fun interval(interval: Interval<String>, isTime: Boolean = true): Boolean {
        try {
            if (isTime) {
                // check if time is in correct format
                if (!time(interval.start) || !time(interval.end)) {
                    return false;
                }

                // check if start >= end
                if (DateFormatter.time(interval.start).after(DateFormatter.time(interval.end))) {
                    return false
                }
            } else {
                // check if time is in correct format
                if (!iso(interval.start) || !iso(interval.end)) {
                    return false;
                }

                // check if start >= end
                if (DateFormatter.iso(interval.start).after(DateFormatter.iso(interval.end))) {
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