package com.scheduling.assistant.endpoints

import com.scheduling.assistant.database.UserRepository
import com.scheduling.assistant.models.User
import com.scheduling.assistant.database.models.UserEntity
import com.scheduling.assistant.utils.Mapper

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

import java.util.TimeZone

import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
class SetupController {
    @Autowired
    lateinit var repository: UserRepository

    var mapper: Mapper = Mapper()
    
    @PostMapping("/setup")
    fun postSetup(timezone: TimeZone, @RequestBody requestBody: User): ResponseEntity<String> {
        var responseCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

        // if (requestBody.schedule.timezone == "") {
        //     requestBody.schedule.timezone = timezone.id
        // }

        try {
            repository.save(mapper.map(requestBody))
            responseCode = HttpStatus.CREATED
        } catch (exception: DataIntegrityViolationException) {
            responseCode = HttpStatus.BAD_REQUEST
            println(exception)
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(responseCode)
    }
}