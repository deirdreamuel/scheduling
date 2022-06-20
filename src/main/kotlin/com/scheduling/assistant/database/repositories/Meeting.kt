package com.scheduling.assistant.database.repositories

import com.scheduling.assistant.database.entities.MeetingEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface MeetingRepository : CrudRepository<MeetingEntity, String> {
    fun getById(id: String): MeetingEntity
    fun findByHost_Email(email: String): List<MeetingEntity>
    fun findByParticipants_Email(email: String): List<MeetingEntity>
    fun findByHost_Id(id: String): List<MeetingEntity>
    fun findByParticipants_Id(id: String): List<MeetingEntity>

    @Query("""
		SELECT DISTINCT m.*
		FROM meetings m
		INNER JOIN meetings_participants p ON p.meeting_entity_id = m.id
		INNER JOIN users u ON u.id = m.host_id OR u.id = p.participants_id
		WHERE u.id = :user_id
		AND m.start_time >= :start
		AND m.end_time <= :end
	""", nativeQuery = true)
    fun findAllByIdBetweenDates(@Param("user_id") id: String, @Param("start") start: String, @Param("end") end: String): List<MeetingEntity>
}