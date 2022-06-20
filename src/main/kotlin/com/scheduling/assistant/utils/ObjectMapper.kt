package com.scheduling.assistant.utils
import com.scheduling.assistant.models.User
import com.scheduling.assistant.models.Schedule
import com.scheduling.assistant.models.Interval
import com.scheduling.assistant.models.Meeting


import com.scheduling.assistant.database.entities.UserEntity
import com.scheduling.assistant.database.entities.ScheduleEntity
import com.scheduling.assistant.database.entities.MeetingEntity

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.springframework.stereotype.Component

@Component
class ObjectMapper {
    private val gson: Gson = GsonBuilder().create()

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

    fun map(schedule: Schedule?): ScheduleEntity? {
        if (schedule == null) {
            return null
        }

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
    
    fun map(scheduleEntity: ScheduleEntity?): Schedule? {
        if (scheduleEntity == null) {
            return null
        }

        val intervalType = object : TypeToken<Interval<String>>(){}.type

        val schedule = Schedule()
        schedule.weekly.monday = gson.fromJson(scheduleEntity.monday, intervalType)
        schedule.weekly.tuesday = gson.fromJson(scheduleEntity.tuesday, intervalType)
        schedule.weekly.wednesday = gson.fromJson(scheduleEntity.wednesday, intervalType)
        schedule.weekly.thursday = gson.fromJson(scheduleEntity.thursday, intervalType)
        schedule.weekly.friday = gson.fromJson(scheduleEntity.friday, intervalType)
        schedule.weekly.saturday = gson.fromJson(scheduleEntity.saturday, intervalType)
        schedule.weekly.sunday = gson.fromJson(scheduleEntity.sunday, intervalType)
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