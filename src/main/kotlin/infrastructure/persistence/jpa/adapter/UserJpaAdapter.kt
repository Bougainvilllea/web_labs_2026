package infrastructure.persistence.jpa.adapter

import domain.model.User
import domain.port.UserRepositoryPort
import infrastructure.persistence.jpa.repository.UserJpaRepository
import infrastructure.persistence.jpa.entity.UserEntity
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("db")
class UserJpaAdapter(
    private val userJpaRepo: UserJpaRepository
) : UserRepositoryPort {

    override fun findAll(): List<User> {
        return userJpaRepo.findAll().map { it.toDomain() }
    }

    override fun findById(id: Long): User? {
        return userJpaRepo.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun create(entity: User): User {
        val userEntity = UserEntity.fromDomain(entity)
        val savedEntity = userJpaRepo.save(userEntity)
        return savedEntity.toDomain()
    }

    override fun update(entity: User): User {
        val existingEntity = userJpaRepo.findById(entity.id!!)
            .orElseThrow { IllegalArgumentException("User with id ${entity.id} not found") }

        val updatedEntity = UserEntity.fromDomain(entity)
        val savedEntity = userJpaRepo.save(updatedEntity)
        return savedEntity.toDomain()
    }

    override fun deleteById(id: Long): Boolean {
        return if (userJpaRepo.existsById(id)) {
            userJpaRepo.deleteById(id)
            true
        } else {
            false
        }
    }

    override fun findByEmail(email: String): User? {
        return userJpaRepo.findByEmail(email)?.toDomain()
    }
}