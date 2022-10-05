package com.scheduling.assistant.models

data class WeeklySchedule (
    var monday: Interval<String> = Interval("09:00 AM", "05:00 PM"),
    var tuesday: Interval<String> = Interval("09:00 AM", "05:00 PM"),
    var wednesday: Interval<String> = Interval("09:00 AM", "05:00 PM"),
    var thursday: Interval<String> = Interval("09:00 AM", "05:00 PM"),
    var friday: Interval<String> = Interval("09:00 AM", "05:00 PM"),
    var saturday: Interval<String> = Interval("00:00 AM", "00:00 AM"),
    var sunday: Interval<String> = Interval("00:00 AM", "00:00 AM")
)