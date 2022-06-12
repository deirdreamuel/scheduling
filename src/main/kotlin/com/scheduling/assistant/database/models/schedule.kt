package com.scheduling.assistant.database.models

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.OneToOne
import javax.persistence.MapsId
import javax.persistence.CascadeType

 
@Entity
@Table(name="SCHEDULE")
class ScheduleEntity {
    @Id 
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    var id: String = ""

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