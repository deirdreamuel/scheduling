package com.scheduling.assistant.engine

import com.scheduling.assistant.database.repositories.MeetingRepository
import com.scheduling.assistant.database.repositories.ScheduleRepository
import com.scheduling.assistant.database.entities.MeetingEntity
import com.scheduling.assistant.extensions.add
import com.scheduling.assistant.extensions.date
import com.scheduling.assistant.extensions.iso
import com.scheduling.assistant.extensions.time
import com.scheduling.assistant.models.Interval
import com.scheduling.assistant.models.Meeting
import com.scheduling.assistant.models.Schedule
import com.scheduling.assistant.utils.DateValidator
import com.scheduling.assistant.utils.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*


@Service
class Scheduler {
    @Autowired
    lateinit var meetingRepository: MeetingRepository

    @Autowired
    lateinit var scheduleRepository: ScheduleRepository

    @Autowired
    lateinit var objectMapper: ObjectMapper

    val validate: DateValidator = DateValidator()

    fun getScheduleOnDay(schedule: Schedule, date: Date): Interval<String> {
        val calendar = Calendar.getInstance()
        calendar.time = date

        return when (calendar[Calendar.DAY_OF_WEEK]) {
            Calendar.SUNDAY -> schedule.weekly.sunday
            Calendar.MONDAY -> schedule.weekly.monday
            Calendar.TUESDAY -> schedule.weekly.tuesday
            Calendar.WEDNESDAY -> schedule.weekly.wednesday
            Calendar.THURSDAY -> schedule.weekly.thursday
            Calendar.FRIDAY -> schedule.weekly.friday
            Calendar.SATURDAY -> schedule.weekly.saturday
            else -> schedule.weekly.monday
        }
    }

    fun findUserAvailabilityOn(id: String, dateText: String): List<Interval<String>> {
        // get user schedule to filter based on instead
        val schedule: Schedule = objectMapper.map(scheduleRepository.getByUser_Id(id))
            ?: return emptyList()

        val scheduleOnDay: Interval<String> = getScheduleOnDay(schedule, dateText.date(schedule.timezone))

        // initialize range of days for filtering
        val startDate = dateText.date(schedule.timezone).add(scheduleOnDay.start.time())
        val endDate = dateText.date(schedule.timezone).add(scheduleOnDay.end.time())

        // add start and end based on schedule for the day
        var meetingIntervals: MutableList<Interval<Date>> = mutableListOf(
            Interval(startDate.add(Calendar.DATE, -1), startDate),
            Interval(endDate, endDate.add(Calendar.DATE, 1))
        )

        meetingIntervals.addAll(
            meetingRepository.findAllByIdBetweenDates(
                id, startDate.iso(), endDate.iso()
            ).map { Interval(it.start_time.iso(), it.end_time.iso()) }
        )

        meetingIntervals.sortBy { it.start }

        // get available time intervals based on meetings
        var availableIntervals: MutableList<Interval<String>> = mutableListOf()
        for (i in 1 until meetingIntervals.count()) {
            if (meetingIntervals[i - 1].end < meetingIntervals[i].start) {
                availableIntervals.add(
                    Interval(meetingIntervals[i - 1].end.iso(), meetingIntervals[i].start.iso())
                )
            }
        }

        return availableIntervals
    }

    fun roundUpToMinutes(originalDate: Date, minToRoundUpTo: Int = 15): Date {
        val calendar = Calendar.getInstance()
        calendar.time = originalDate

        val originalMinutes = calendar[Calendar.MINUTE]
        val remainder = originalMinutes % minToRoundUpTo

        if (remainder > 0) {
            calendar.add(Calendar.MINUTE, minToRoundUpTo - remainder)
        }

        return calendar.time
    }

    fun suggestMeetings(userId: String, dateText: String, duration: Int): List<Interval<String>> {
        val availableIntervals: List<Interval<String>> = findUserAvailabilityOn(userId, dateText)
        var suggestedMeetings: MutableList<Interval<String>> = mutableListOf()

        for (interval in availableIntervals) {
            var suggestStart = roundUpToMinutes(interval.start.iso())
            var suggestEnd = suggestStart.add(Calendar.MINUTE, duration)

            while (suggestEnd <= interval.end.iso()) {
                suggestedMeetings.add(Interval(suggestStart.iso(), suggestEnd.iso()))
                suggestStart = suggestEnd
                suggestEnd = suggestEnd.add(Calendar.MINUTE, duration)
            }
        }

        return suggestedMeetings
    }

    // TODO: merge/intersect intervals for multiple availabilities
    //

    fun isMeetingAvailable(email: String, meeting: Meeting): Boolean {
        if (!validate.interval(Interval(meeting.start_time, meeting.end_time), false)) {
            return false
        }

        val userMeetings: Iterable<MeetingEntity> = meetingRepository.findByHost_Email(email)

        for (userMeeting: MeetingEntity in userMeetings) {
            val isBusy = isOverlap(
                Interval(meeting.start_time.iso(), meeting.end_time.iso()),
                Interval(userMeeting.start_time.iso(), userMeeting.end_time.iso())
            )

            if (isBusy) return false
        }

        return true
    }

    private fun isOverlap (newMeeting: Interval<Date>, otherMeeting: Interval<Date>): Boolean {
        return (newMeeting.start < otherMeeting.end && newMeeting.end > otherMeeting.start)
    }
}

