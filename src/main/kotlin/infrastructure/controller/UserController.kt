package infrastructure.controller

import application.dto.response.UserResponse
import domain.model.User
import domain.service.UserService
import org.example.example.infrastructure.dto.requests.user.UserData
import org.example.example.infrastructure.dto.requests.user.UserUpdateRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shared.mapper.UserMapper


@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper
) {

    @GetMapping
    fun fetchAllUsers(): ResponseEntity<List<UserResponse>> {
        val domainUsers: List<User> = userService.getAllUsers()
        val responseData: List<UserResponse> = domainUsers.map { userMapper.toResponse(it) }
        return ResponseEntity.ok(responseData)
    }

    @PostMapping
    fun registerNewUser(@RequestBody userData: UserData): ResponseEntity<UserResponse> {
        val userFromRequest = userMapper.toDomain(userData)
        val (savedUser, isNew) = userService.createOrGetUser(userFromRequest)
        val responseData = userMapper.toResponse(savedUser)

        val httpStatus = if (isNew) HttpStatus.CREATED else HttpStatus.OK
        return ResponseEntity.status(httpStatus).body(responseData)
    }

    @GetMapping("/{id}")
    fun retrieveUserById(@PathVariable id: Int): ResponseEntity<UserResponse> {
        val user = userService.getUserById(id.toLong())
        val responseData = userMapper.toResponse(user)
        return ResponseEntity.ok(responseData)
    }

    @PutMapping("/{id}")
    fun modifyExistingUser(@PathVariable id: Int, @RequestBody updateRequest: UserUpdateRequest): ResponseEntity<UserResponse> {
        val user = userMapper.toDomain(updateRequest).copy(id = id.toLong())
        val updatedUser = userService.updateUser(user)
        val responseData = userMapper.toResponse(updatedUser)
        return ResponseEntity.ok(responseData)
    }

    @DeleteMapping("/{id}")
    fun removeUserById(@PathVariable id: Int): ResponseEntity<Boolean> {
        val deletionResult = userService.deleteUserById(id.toLong())
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(deletionResult)
    }
}