package com.scheduling.assistant.utils

import com.scheduling.assistant.models.Interval
import java.util.Date

import com.scheduling.assistant.database.MeetingRepository
import com.scheduling.assistant.database.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

import com.scheduling.assistant.models.Meeting
import com.scheduling.assistant.database.models.MeetingEntity

@Component
class Availability {
    @Autowired
    lateinit var meetingRepository: MeetingRepository

    @Autowired
    lateinit var userRepository: UserRepository

    val dateUtil: Formatter = Formatter()
    val validate: Validator = Validator()


    // fun generate(
    //     schedule: WeeklySchedule, 
    //     meetings: List<Interval>, 
    //     range: Duration = Duration.ofDays(30)
    // ): List<Interval> {
    //     // generate availability based on schedule



    //     return emptyList()
    // }
    fun checkSchedule(email: String, meeting: Meeting): Boolean {
        // check if meeting >= today
        // if (formatter.iso(meeting.start_time).after(when))
        // val user: UserEntity = userRepository.getByEmail(email)

        // val schedule: ScheduleEntity = user.schedule

        // println(ScheduleEntity::class.java.getField("monday").get(schedule))
        return true
    }

    fun checkMeeting(email: String, meeting: Meeting): Boolean {
        if (!validate.interval(Interval(meeting.start_time, meeting.end_time), false)) {
            return false
        }

        val userMeetings: Iterable<MeetingEntity> = meetingRepository.findByHost_Email(email)

        for (userMeeting: MeetingEntity in userMeetings) {
            val isBusy = checkIfBusy(
                Interval(meeting.start_time, meeting.end_time),
                Interval(userMeeting.start_time, userMeeting.end_time)
            )

            if (isBusy) {
                return false
            }
        }

        return true
    }

    private fun checkIfBusy (meetingA: Interval, meetingB: Interval): Boolean {
        val startA: Date = dateUtil.iso(meetingA.start)
        val endA: Date = dateUtil.iso(meetingA.end)

        val startB: Date = dateUtil.iso(meetingB.start)
        val endB: Date = dateUtil.iso(meetingB.end)


        println("$startA - $endA : $startB - $endB")
        println(startA <= endB && endA >= startB)
        println()
        return ((startA == startB && endA == endB) || (startA < endB && endA > startB))
    }
}

