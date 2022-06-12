package com.scheduling.assistant.endpoints

import com.scheduling.assistant.database.UserRepository
import com.scheduling.assistant.models.Meeting
import com.scheduling.assistant.models.Response

import com.scheduling.assistant.database.models.UserEntity
import com.scheduling.assistant.database.models.MeetingEntity
import com.scheduling.assistant.database.MeetingRepository
import com.scheduling.assistant.models.Messages
import com.scheduling.assistant.utils.Mapper

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException

import org.springframework.http.ResponseEntity
import org.springframework.http.HttpStatus

import com.scheduling.assistant.utils.Availability
import com.scheduling.assistant.utils.ResponseHandler
import java.io.IOException

@RestController
class MeetingController {
    @Autowired
    lateinit var meetingDB: MeetingRepository

    @Autowired
    lateinit var userDB: UserRepository

    @Autowired
    lateinit var availability: Availability

    @Autowired
    lateinit var responseHandler: ResponseHandler

    @Autowired
    lateinit var mapper: Mapper

    @GetMapping("/meetings/{id}")
    fun getSchedule(@PathVariable id: String): ResponseEntity<Iterable<MeetingEntity>> {
        // find user id in database & check his schedule
        // check preference in months &/ weeks ahead
        var responseCode: HttpStatus = HttpStatus.INTERNAL_SERVER_ERROR
        var userEntity: Set<MeetingEntity>? = null

        try {
            userEntity = meetingDB.findByParticipants_Id(id)
            userEntity.plus(meetingDB.findByHost_Id(id))

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
            val meeting: MeetingEntity = mapper.map(requestBody)
            val host: UserEntity = getUserFromIdOrEmail(id, email)
                ?: return responseHandler.respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)

            // check host's availability
            if (!availability.checkMeeting(host.email, requestBody)) {
                return responseHandler.respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)
            }

            // check each participant's availability
            // TODO: Get participants & check if each has a user account
            val participantEmails = requestBody.participants.map{ obj -> obj.email }
            for (participantEmail in participantEmails) {
                if (!availability.checkMeeting(participantEmail, requestBody)) {
                    return responseHandler.respond(HttpStatus.BAD_REQUEST, Messages.PARTICIPANTS_UNAVAILABLE)
                }
            }

            meeting.participants = userDB.findByEmailIn(participantEmails)
            meetingDB.save(meeting)

            return responseHandler.respond(HttpStatus.CREATED, Messages.CREATE_MEETING_SUCCESS)
        } catch (exception: DataIntegrityViolationException) {
            println(exception)
        } catch (exception: Exception) {
            println(exception)
            println(exception.printStackTrace())
        }
        
        return responseHandler.respond(HttpStatus.INTERNAL_SERVER_ERROR, Messages.UNEXPECTED_ERROR)
    }

    fun getUserFromIdOrEmail(id: String?, email: String?): UserEntity? {
        if (id != null) {
            return userDB.getById(id)
        }
        
        if (email != null) {
            return userDB.getByEmail(email)
        }

        return  null
    }
}