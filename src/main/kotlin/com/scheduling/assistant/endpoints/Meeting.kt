package com.scheduling.assistant.endpoints

import com.scheduling.assistant.database.repositories.MeetingRepository
import com.scheduling.assistant.database.repositories.UserRepository
import com.scheduling.assistant.database.entities.MeetingEntity
import com.scheduling.assistant.database.entities.UserEntity
import com.scheduling.assistant.models.Meeting
import com.scheduling.assistant.models.Messages
import com.scheduling.assistant.models.Response
import com.scheduling.assistant.engine.Scheduler
import com.scheduling.assistant.utils.ObjectMapper
import com.scheduling.assistant.utils.ResponseHandler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.IOException

@RestController
class Meeting {
    @Autowired
    lateinit var meetingRepository: MeetingRepository

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var scheduler: Scheduler

    @Autowired
    lateinit var objectMapper: ObjectMapper

    @Autowired
    lateinit var responseHandler: ResponseHandler

    @GetMapping("/meetings/{id}")
    fun getSchedule(@PathVariable id: String): ResponseEntity<Iterable<MeetingEntity>> {
        // find user id in database & check his schedule
        // check preference in months &/ weeks ahead
        var responseCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        var userEntity: List<MeetingEntity>? = null

        try {
            userEntity = meetingRepository.findByParticipants_Id(id)
            userEntity = userEntity.plus(meetingRepository.findByHost_Id(id))

            responseCode = HttpStatus.OK
        } catch (exception: IOException) {
            println(exception)
        }

        return ResponseEntity(userEntity, responseCode)
    }


    @PostMapping("/meetings")
    fun postMeeting(
        @RequestBody requestBody: Meeting,
        @RequestParam(required = false) id: String?,
        @RequestParam(required = false) email: String?
    ): ResponseEntity<Response> {
        try {
            val meeting: MeetingEntity = objectMapper.map(requestBody)
            val host: UserEntity = getUserFromIdOrEmail(id, email)
                ?: return responseHandler.respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)

            // check host's availability
            if (!scheduler.isMeetingAvailable(host.email, requestBody)) {
                return responseHandler.respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)
            }

            // get participants & check if each has a user account
            for (participant in requestBody.participants) {
                val user: UserEntity? = userRepository.getByEmail(participant.email)

                if (user == null) {
                    userRepository.save(objectMapper.map(participant))
                }

                // check each participant's availability
                if (!scheduler.isMeetingAvailable(participant.email, requestBody)) {
                    return responseHandler.respond(HttpStatus.BAD_REQUEST, Messages.PARTICIPANTS_UNAVAILABLE)
                }
            }

            val emails = requestBody.participants.map { obj -> obj.email }
            meeting.host = host
            meeting.participants = userRepository.findByEmailIn(emails)

            meetingRepository.save(meeting)

            return responseHandler.respond(HttpStatus.CREATED, Messages.CREATE_MEETING_SUCCESS)
        } catch (exception: Exception) {
            println(exception)
            println(exception.printStackTrace())
        }

        return responseHandler.respond(HttpStatus.INTERNAL_SERVER_ERROR, Messages.UNEXPECTED_ERROR)
    }

    fun getUserFromIdOrEmail(id: String?, email: String?): UserEntity? {
        if (id != null) {
            return userRepository.getById(id)
        }

        if (email != null) {
            return userRepository.getByEmail(email)
        }

        return null
    }
}