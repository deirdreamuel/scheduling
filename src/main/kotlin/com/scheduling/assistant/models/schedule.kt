package com.scheduling.assistant.models

import java.time.Duration
import java.time.Instant
import java.util.Date

data class Schedule (
    var weekly: WeeklySchedule = WeeklySchedule(),
    var timezone: String = ""
)

data class WeeklySchedule (
    var monday: Interval = Interval("09:00 AM", "05:00 PM"),
    var tuesday: Interval = Interval("09:00 AM", "05:00 PM"),
    var wednesday: Interval = Interval("09:00 AM", "05:00 PM"),
    var thursday: Interval = Interval("09:00 AM", "05:00 PM"),
    var friday: Interval = Interval("09:00 AM", "05:00 PM"),
    var saturday: Interval = Interval("00:00 AM", "00:00 AM"),
    var sunday: Interval = Interval("00:00 AM", "00:00 AM")
)