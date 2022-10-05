package com.scheduling.assistant.models

data class Schedule (
    var weekly: WeeklySchedule = WeeklySchedule(),
    var timezone: String = ""
)
