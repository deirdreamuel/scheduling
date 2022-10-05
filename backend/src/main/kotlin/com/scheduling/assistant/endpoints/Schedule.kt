package com.scheduling.assistant.endpoints

import com.google.gson.Gson
import com.scheduling.assistant.database.repositories.ScheduleRepository
import com.scheduling.assistant.database.repositories.UserRepository
import com.scheduling.assistant.database.entities.ScheduleEntity
import com.scheduling.assistant.database.entities.UserEntity
import com.scheduling.assistant.models.Schedule
import com.scheduling.assistant.utils.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class Schedule {
    @Autowired
    lateinit var userRepository: UserRepository
    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @GetMapping("/schedule/{id}")
    fun getSchedule(timezone: TimeZone, @PathVariable id: String): ResponseEntity<Schedule?> {
        // find user id in database & check his schedule
        // check favorability in months &/ weeks ahead
        println(timezone.id + "SCHEDULE???")
        println(id)
        var responseCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        var schedule: Schedule? = null

        try {
            val scheduleEntity: ScheduleEntity? = scheduleRepository.getByUser_Id(id)

            schedule = objectMapper.map(scheduleEntity)
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
    fun postSchedule(
        timezone: TimeZone,
        @RequestBody requestBody: Schedule,
        @PathVariable id: String
    ): ResponseEntity<String> {
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
            val user: UserEntity? = userRepository.getById(id)
            scheduleEntity = objectMapper.map(requestBody)
            if (scheduleEntity == null) {
                return ResponseEntity(HttpStatus.BAD_REQUEST)
            }

            // TODO: remove once patch is implemented
            val existingSchedule = scheduleRepository.getByUser_Id(id)
            if (existingSchedule != null) {
                existingSchedule.monday = scheduleEntity.monday
                existingSchedule.tuesday = scheduleEntity.tuesday
                existingSchedule.wednesday = scheduleEntity.wednesday
                existingSchedule.thursday = scheduleEntity.thursday
                existingSchedule.friday = scheduleEntity.friday
                existingSchedule.saturday = scheduleEntity.saturday
                existingSchedule.sunday = scheduleEntity.sunday
                existingSchedule.timezone = scheduleEntity.timezone
                scheduleRepository.save(existingSchedule)
                return ResponseEntity(HttpStatus.CREATED)
            }

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

    // TODO: patch mapping for changing schedule information
}