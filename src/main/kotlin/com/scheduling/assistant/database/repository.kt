package com.scheduling.assistant.database

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import javax.persistence.Query

import com.scheduling.assistant.database.models.UserEntity
import com.scheduling.assistant.database.models.MeetingEntity
import com.scheduling.assistant.database.models.ScheduleEntity


@Repository
interface UserRepository : CrudRepository<UserEntity, String> {
	fun findByEmail(email: String): Iterable<UserEntity>
	fun getByEmail(email: String): UserEntity
	fun getById(id: String): UserEntity
    fun findByEmailIn(emails: List<String>): Set<UserEntity>
}

@Repository
interface MeetingRepository : CrudRepository<MeetingEntity, String> {
	fun getById(id: String): MeetingEntity
    fun findByHost_Email(email: String): Set<MeetingEntity>
    fun findByParticipants_Email(email: String): Set<MeetingEntity>
    fun findByHost_Id(id: String): Set<MeetingEntity>
    fun findByParticipants_Id(id: String): Set<MeetingEntity>
}

@Repository
interface ScheduleRepository : CrudRepository<ScheduleEntity, String> {
	fun getByUser_Email(email: String): ScheduleEntity
	fun getByUser_Id(id: String): ScheduleEntity
}

