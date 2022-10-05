package com.scheduling.assistant.utils

import com.scheduling.assistant.models.Response
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component


@Component
class ResponseHandler {
    fun respond(status: HttpStatus, msg: String): ResponseEntity<Response> {
        val responseObj = Response(status.value(), msg)
        return ResponseEntity(responseObj, status)
    }
}