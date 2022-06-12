package com.scheduling.assistant.endpoints

import com.scheduling.assistant.database.UserRepository
import com.scheduling.assistant.database.ScheduleRepository


import com.scheduling.assistant.models.Schedule
import com.scheduling.assistant.models.WeeklySchedule
import com.scheduling.assistant.models.Interval
import com.scheduling.assistant.utils.Formatter
import com.scheduling.assistant.utils.Validator


import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.PathVariable

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

import java.util.TimeZone

import com.google.gson.Gson

import java.util.Date

import java.time.LocalDateTime
import java.time.LocalTime

import java.util.Calendar
import java.util.Locale
import java.text.SimpleDateFormat

import com.scheduling.assistant.models.User
import com.scheduling.assistant.database.models.UserEntity
import com.scheduling.assistant.database.models.ScheduleEntity


import com.scheduling.assistant.utils.Mapper
import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

@RestController
class ScheduleController {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    var mapper: Mapper = Mapper()
    
    val validator: Validator = Validator()

    @GetMapping("/schedule/{id}")
    fun getSchedule(timezone: TimeZone, @PathVariable id: String): ResponseEntity<Schedule> {
        // find user id in database & check his schedule
        // check favorability in months &/ weeks ahead
        println(timezone.id + "SCHEDULE???")
        println(id)
        var responseCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        var schedule: Schedule? = null

        try {
            val scheduleEntity: ScheduleEntity = scheduleRepository.getByUser_Id(id)

            schedule = mapper.map(scheduleEntity)
            responseCode = HttpStatus.OK
        } catch (exception: DataIntegrityViolationException) {
            responseCode = HttpStatus.BAD_REQUEST
            println(exception)
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(schedule, responseCode)
    }

    @PostMapping("/schedule/{id}")
    fun postSchedule(timezone: TimeZone, @RequestBody requestBody: Schedule, @PathVariable id: String): ResponseEntity<String> {
        // find user id in database & check his schedule
        // check favorability in months &/ weeks ahead
        println(timezone.id + "SCHEDULE???")
        println(id)
        var responseCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR

        if (requestBody.timezone == "") {
            requestBody.timezone = timezone.id
        }

        var scheduleEntity: ScheduleEntity? = null

        try {
            val user: UserEntity = userRepository.getById(id)
            scheduleEntity = mapper.map(requestBody)

            println(Gson().toJson(requestBody))
            println(Gson().toJson(scheduleEntity))

            scheduleEntity.user = user

            scheduleRepository.save(scheduleEntity)
            responseCode = HttpStatus.OK
        } catch (exception: DataIntegrityViolationException) {
            responseCode = HttpStatus.BAD_REQUEST
            println(exception)
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(responseCode)
    }
}