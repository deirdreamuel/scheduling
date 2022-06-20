package com.scheduling.assistant.database.repositories

import com.scheduling.assistant.database.entities.UserEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<UserEntity, String> {
    fun findByEmail(email: String): List<UserEntity>
    fun getByEmail(email: String): UserEntity?
    fun getById(id: String): UserEntity?
    fun findByEmailIn(emails: List<String>): List<UserEntity>
}