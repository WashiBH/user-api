package com.bci.api.service.impl

import com.bci.api.configuration.JwtTokenProvider
import com.bci.api.dto.request.UserCreateRequest
import com.bci.api.dto.response.UserCreateResponse
import com.bci.api.model.UserModel
import com.bci.api.repository.UserRepository
import spock.lang.Specification
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.mockito.Mock
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations
import org.mockito.stubbing.Answer
import spock.lang.Subject

import java.time.LocalDateTime
import java.util.UUID

@Subject(UserServiceImpl)
class UserServiceImplSpec extends Specification {

    private UserRepository userRepository = Mock(UserRepository)
    private PasswordEncoder passwordEncoder = Mock(PasswordEncoder)
    private JwtTokenProvider tokenProvider = Mock(JwtTokenProvider)

    def userService

    def setup() {
        userService = new UserServiceImpl(userRepository, passwordEncoder, tokenProvider)
    }

    def "should create user successfully"() {
        given:
        UUID userId = UUID.randomUUID()
        UserCreateRequest userCreateRequest = new UserCreateRequest("Juan Perez", "juan@gmail.com", "12345", [])
        def encodedPasswordMock = passwordEncoder.encode(userCreateRequest.getContrasena())
        def generatedTokenMock = "token"

        userRepository.existsByEmail(userCreateRequest.getCorreo()) >> false
        userRepository.save(_) >> { UserModel user ->
            user.setAuditCreatedDate(LocalDateTime.now())
            user.setUserId(userId)
            return user
        }
        tokenProvider.generateToken(userCreateRequest.getCorreo()) >> generatedTokenMock

        when:

        UserCreateResponse userCreateResponse = userService.createUser(userCreateRequest)

        then:
        userCreateResponse.getId() == userId
        userCreateResponse.getNombre() == "Juan Perez"
        userCreateResponse.getCorreo() == "juan@gmail.com"
        userCreateResponse.getToken() == generatedTokenMock
    }
}

