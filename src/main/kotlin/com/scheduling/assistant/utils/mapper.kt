package com.scheduling.assistant.utils
import com.scheduling.assistant.models.User
import com.scheduling.assistant.models.Schedule
import com.scheduling.assistant.models.Interval
import com.scheduling.assistant.models.Meeting


import com.scheduling.assistant.database.models.UserEntity
import com.scheduling.assistant.database.models.ScheduleEntity
import com.scheduling.assistant.database.models.MeetingEntity

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import org.springframework.stereotype.Component

@Component
class Mapper {
    private val gson: Gson = GsonBuilder().create();

    fun map(user: User): UserEntity {
        val userEntity = UserEntity()

        userEntity.firstName = user.first_name
        userEntity.lastName = user.last_name
        userEntity.email = user.email

        return userEntity
    }

    fun map(userEntity: UserEntity): User {
        val user = User()

        user.first_name = userEntity.firstName
        user.last_name = userEntity.lastName
        user.email = userEntity.email

        return user
    }

    fun map(schedule: Schedule): ScheduleEntity {
        val scheduleEntity = ScheduleEntity()
        
        scheduleEntity.monday = gson.toJson(schedule.weekly.monday)
        scheduleEntity.tuesday = gson.toJson(schedule.weekly.tuesday)
        scheduleEntity.wednesday = gson.toJson(schedule.weekly.wednesday)
        scheduleEntity.thursday = gson.toJson(schedule.weekly.thursday)
        scheduleEntity.friday = gson.toJson(schedule.weekly.friday)
        scheduleEntity.saturday = gson.toJson(schedule.weekly.saturday)
        scheduleEntity.sunday = gson.toJson(schedule.weekly.sunday)
        scheduleEntity.timezone = schedule.timezone

        return scheduleEntity
    }
    
    fun map(scheduleEntity: ScheduleEntity): Schedule {
        val schedule = Schedule()
        
        schedule.weekly.monday = gson.fromJson(scheduleEntity.monday, Interval::class.java)
        schedule.weekly.tuesday = gson.fromJson(scheduleEntity.tuesday, Interval::class.java)
        schedule.weekly.wednesday = gson.fromJson(scheduleEntity.wednesday, Interval::class.java)
        schedule.weekly.thursday = gson.fromJson(scheduleEntity.thursday, Interval::class.java)
        schedule.weekly.friday = gson.fromJson(scheduleEntity.friday, Interval::class.java)
        schedule.weekly.saturday = gson.fromJson(scheduleEntity.saturday, Interval::class.java)
        schedule.weekly.sunday = gson.fromJson(scheduleEntity.sunday, Interval::class.java)
        schedule.timezone = scheduleEntity.timezone

        return schedule
    }

    fun map(meeting: Meeting): MeetingEntity {
        val meetingEntity = MeetingEntity()

        meetingEntity.start_time = meeting.start_time
        meetingEntity.end_time = meeting.end_time

        meetingEntity.title = meeting.title
        meetingEntity.notes = meeting.notes

        return meetingEntity
    }
}