package application.service

import domain.model.User
import domain.port.UserRepositoryPort
import domain.service.UserService
import org.springframework.stereotype.Service
import shared.exception.BusinessException

@Service
class UserService(
    private val userRepository: UserRepositoryPort
) : UserService {

    override fun getUserById(userId: Long): User {
        return userRepository.findById(userId)
            ?: throw BusinessException.UserNotFound(userId)
    }

    override fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

    override fun findByEmail(emailAddress: String): User? {
        return userRepository.findByEmail(emailAddress)
    }

    override fun updateUser(userToUpdate: User): User {
        val existingUser = userRepository.findById(userToUpdate.id!!)
            ?: throw BusinessException.UserNotFound(userToUpdate.id!!)

        val userWithSameEmail = findByEmail(userToUpdate.email)
        if (userWithSameEmail != null && userWithSameEmail.id != userToUpdate.id) {
            throw BusinessException.EmailAlreadyExists(userToUpdate.email)
        }

        return userRepository.update(userToUpdate)
    }

    override fun createOrGetUser(userToCreate: User): Pair<User, Boolean> {
        val existingUser = findByEmail(userToCreate.email)

        if (existingUser != null) {
            return Pair(existingUser, false)
        }

        return try {
            Pair(userRepository.create(userToCreate), true)
        } catch (e: IllegalArgumentException) {
            throw BusinessException.UserValidationError()
        }
    }

    override fun deleteUserById(userId: Long): Boolean {
        val userExists = userRepository.findById(userId)
            ?: throw BusinessException.UserNotFound(userId)

        return userRepository.deleteById(userId)
    }
}