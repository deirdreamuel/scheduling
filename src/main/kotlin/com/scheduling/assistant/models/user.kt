package com.scheduling.assistant.models
import java.util.UUID;

data class User (
    var id: String = UUID.randomUUID().toString(),
    var first_name: String = "",
    var last_name: String = "",
    var email: String = ""
)