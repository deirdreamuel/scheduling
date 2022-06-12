package com.scheduling.assistant.database.models

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.OneToOne
import javax.persistence.OneToMany
import javax.persistence.ManyToMany


import javax.persistence.Column
import javax.persistence.ElementCollection

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="meetings")
class MeetingEntity {
    @Id 
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    var id: String = ""

    var start_time: String = ""
    var end_time: String = ""

    var title: String = ""
    var notes: String = ""
    var link: String = ""

    @OneToOne
    var host: UserEntity? = null

    @ManyToMany
    var participants: Set<UserEntity>? = null
}