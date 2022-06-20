package com.scheduling.assistant.endpoints

import com.scheduling.assistant.models.Interval
import com.scheduling.assistant.engine.Scheduler
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
class Availability {
    @Autowired
    lateinit var scheduler: Scheduler

    @GetMapping("/availability/{id}")
    fun getAvailability(
        timezone: TimeZone,
        @PathVariable id: String,
        @RequestParam date: String
    ): ResponseEntity<Iterable<Interval<String>>> {
        try {
            return ResponseEntity(scheduler.findUserAvailabilityOn(id, date), HttpStatus.OK)
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/suggestions/{id}")
    fun getSuggestions(
        timezone: TimeZone,
        @PathVariable id: String,
        @RequestParam date: String,
        @RequestParam duration: Int
    ): ResponseEntity<Iterable<Interval<String>>> {
        try {
            return ResponseEntity(scheduler.suggestMeetings(id, date, duration), HttpStatus.OK)
        } catch (exception: Exception) {
            println(exception)
        }

        return ResponseEntity(null, HttpStatus.INTERNAL_SERVER_ERROR)
    }
}