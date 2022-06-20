package com.scheduling.assistant.database.entities

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.OneToOne


@Entity
@Table(name="SCHEDULE")
class ScheduleEntity {
    @Id 
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    var id: String = ""

    // TODO: make user unique
    @OneToOne
    var user: UserEntity? = null

    var monday: String = ""
    var tuesday: String = ""
    var wednesday: String = ""
    var thursday: String = ""
    var friday: String = ""
    var saturday: String = ""
    var sunday: String = ""

    var timezone: String = ""
}