package com.scheduling.assistant.endpoints

import com.scheduling.assistant.database.repositories.MeetingRepository
import com.scheduling.assistant.database.repositories.UserRepository
import com.scheduling.assistant.database.entities.MeetingEntity
import com.scheduling.assistant.database.entities.UserEntity
import com.scheduling.assistant.models.Meeting
import com.scheduling.assistant.models.Messages
import com.scheduling.assistant.engine.Scheduler
import com.scheduling.assistant.utils.ObjectMapper
import com.scheduling.assistant.utils.ResponseHandler
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.Spy
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.HttpStatus

@SpringBootTest
class MeetingsTests {
    @MockBean
    lateinit var userRepository: UserRepository
    @MockBean
    lateinit var scheduler: Scheduler
    @MockBean
    lateinit var objectMapper: ObjectMapper

    @Spy
    lateinit var responseHandler: ResponseHandler
    @Spy
    lateinit var meetingRepository: MeetingRepository

    @InjectMocks
    lateinit var meetingController: com.scheduling.assistant.endpoints.Meeting

    private var validHost: String = "valid-host"
    private var invalidHost: String = "invalid-host"

    private var validMeeting = Meeting(title = "valid-meeting")
    private var invalidMeeting = Meeting(title = "invalid-meeting")

    private fun <T> any(type: Class<T>): T = Mockito.any<T>(type)

    // TODO: mock meeting entity from mapper.map() and make sure host and participants are populated

    @BeforeEach
    fun setup() {
        val validUserEntity = UserEntity()
        validUserEntity.email = validHost

        Mockito.doReturn(validUserEntity).`when`(userRepository).getById(validHost)
        Mockito.doReturn(validUserEntity).`when`(userRepository).getByEmail(validHost)

        Mockito.doReturn(null).`when`(userRepository).getById(invalidHost)
        Mockito.doReturn(null).`when`(userRepository).getByEmail(invalidHost)

        Mockito.doReturn(MeetingEntity()).`when`(objectMapper).map(any(Meeting::class.java))

        Mockito.doReturn(true).`when`(scheduler).isMeetingAvailable(validHost, validMeeting)
        Mockito.doReturn(false).`when`(scheduler).isMeetingAvailable(validHost, invalidMeeting)
        Mockito.doReturn(false).`when`(scheduler).isMeetingAvailable(invalidHost, validMeeting)

        MockitoAnnotations.openMocks(this)
    }

    @Test
    fun `WHEN_create_meeting_valid_using_id_THEN_responds_created`() {
        meetingController.postMeeting(validMeeting, validHost, null)
        Mockito.verify(meetingRepository, times(1)).save(any(MeetingEntity::class.java))
        Mockito.verify(responseHandler).respond(HttpStatus.CREATED, Messages.CREATE_MEETING_SUCCESS)
    }

    @Test
    fun `WHEN_create_meeting_valid_using_email_THEN_responds_created`() {
        meetingController.postMeeting(validMeeting, null, validHost)
        Mockito.verify(meetingRepository, times(1)).save(any(MeetingEntity::class.java))
        Mockito.verify(responseHandler).respond(HttpStatus.CREATED, Messages.CREATE_MEETING_SUCCESS)
    }

    @Test
    fun `WHEN_create_meeting_no_id_or_email_THEN_responds_bad_request`() {
        meetingController.postMeeting(validMeeting, null, null)
        Mockito.verify(meetingRepository, times(0)).save(any(MeetingEntity::class.java))
        Mockito.verify(responseHandler).respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)
    }

    @Test
    fun `WHEN_create_meeting_host_not_found_by_id_THEN_responds_bad_request`() {
        meetingController.postMeeting(validMeeting, invalidHost, null)
        Mockito.verify(meetingRepository, times(0)).save(any(MeetingEntity::class.java))
        Mockito.verify(responseHandler).respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)
    }

    @Test
    fun `WHEN_create_meeting_host_not_found_by_email_THEN_responds_bad_request`() {
        meetingController.postMeeting(validMeeting, null, invalidHost)
        Mockito.verify(meetingRepository, times(0)).save(any(MeetingEntity::class.java))
        Mockito.verify(responseHandler).respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)
    }

    @Test
    fun `WHEN_create_meeting_host_unavailable_THEN_responds_bad_request`() {
        meetingController.postMeeting(invalidMeeting, validHost, null)
        Mockito.verify(meetingRepository, times(0)).save(any(MeetingEntity::class.java))
        Mockito.verify(responseHandler).respond(HttpStatus.BAD_REQUEST, Messages.HOST_UNAVAILABLE)
    }
}