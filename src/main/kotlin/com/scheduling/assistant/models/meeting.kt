package com.scheduling.assistant.models

data class Meetings (
    var meetings: List<Meeting> = emptyList()
)

data class Meeting (
    var start_time: String = "",
    var end_time: String = "",

    var title: String = "",
    var notes: String = "",
    var participants: Set<User> = emptySet()
)