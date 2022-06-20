package com.scheduling.assistant.models

data class Meeting (
    var start_time: String = "",
    var end_time: String = "",

    var title: String = "",
    var notes: String = "",
    var participants: List<User> = emptyList()
)