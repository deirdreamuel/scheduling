package com.scheduling.assistant.database.models

import javax.persistence.Entity
import javax.persistence.Table
import javax.persistence.Column
import javax.persistence.Id
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.OneToOne
import javax.persistence.JoinColumn

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="USERS")
class UserEntity {
    @Id 
    @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    var id: String = ""
    
    @Column(unique=true)
    var email: String = ""

    @Column(name="first_name")
    var firstName: String = ""

    @Column(name="last_name")
    var lastName: String = ""
}