package org.example.example.infrastructure.persistence.mock.adapter

import domain.model.User
import domain.port.UserRepositoryPort
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
@Profile("mock")
class MockUserRepositoryAdapter : UserRepositoryPort {
    protected val dataStore = ConcurrentHashMap<Long, User>()
    private val sequenceGenerator = AtomicLong(0)

    override fun findByEmail(email: String): User? {
        return dataStore.values.find { it.email.equals(email, ignoreCase = true) }
    }

    override fun findAll(): List<User> {
        return dataStore.values.toList()
    }

    override fun findById(id: Long): User? {
        return dataStore[id]
    }

    override fun create(entity: User): User{
        val entityId = entity.id ?: sequenceGenerator.getAndIncrement()
        val entityWithId = entity.withId(entityId)
        dataStore[entityId] = entityWithId
        return entityWithId
    }

    override fun update(entity: User): User {
        val entityId = entity.id ?: throw IllegalArgumentException("Cannot update entity without ID")
        dataStore[entityId] = entity
        return entity
    }

    override fun deleteById(id: Long): Boolean{
        return dataStore.remove(id) != null
    }
}