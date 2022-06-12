package com.scheduling.assistant.utils

import com.google.gson.Gson
import com.scheduling.assistant.models.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component


@Component
class ResponseHandler {
    var gson = Gson()
    fun respond(status: HttpStatus, msg: String): ResponseEntity<Response> {
        val responseObj: Response = Response(status.value(), msg)
        return ResponseEntity(responseObj, status)
    }
}