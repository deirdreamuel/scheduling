package com.scheduling.assistant.endpoints

import com.scheduling.assistant.database.repositories.UserRepository
import com.scheduling.assistant.models.User
import com.scheduling.assistant.utils.ObjectMapper

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

import java.util.TimeZone

import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
class User {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var  objectMapper: ObjectMapper
    
    @PostMapping("/user")
    fun postSetup(timezone: TimeZone, @RequestBody requestBody: User): ResponseEntity<String> {
        try {
            userRepository.save(objectMapper.map(requestBody))
            return ResponseEntity(HttpStatus.CREATED)

        } catch (exception: DataIntegrityViolationException) {
            println(exception)
            return ResponseEntity(HttpStatus.BAD_REQUEST)
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // TODO: patch mapping for changing user information
}