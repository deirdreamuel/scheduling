package com.scheduling.assistant.database.repositories

import com.scheduling.assistant.database.entities.ScheduleEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ScheduleRepository : CrudRepository<ScheduleEntity, String> {
    fun getByUser_Email(email: String): ScheduleEntity?
    fun getByUser_Id(id: String): ScheduleEntity?
}
