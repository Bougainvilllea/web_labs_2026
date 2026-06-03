package org.example.example.infrastructure.persistence.mock.adapter
import domain.model.Dish
import domain.port.DishRepositoryPort
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong

@Repository
@Profile("mock")
class MockDishRepositoryAdapter : DishRepositoryPort {
    protected val dataStore = ConcurrentHashMap<Long, Dish>()
    private val sequenceGenerator = AtomicLong(0)

    override fun findByName(name: String): Dish? {
        return dataStore.values.find { it.name.equals(name, ignoreCase = true) }
    }

    override fun findAll(): List<Dish> {
        return dataStore.values.toList()
    }

    override fun findById(id: Long): Dish? {
        return dataStore[id]
    }

    override fun create(entity: Dish): Dish{
        val entityId = entity.id ?: sequenceGenerator.getAndIncrement()
        val entityWithId = entity.withId(entityId)
        dataStore[entityId] = entityWithId
        return entityWithId
    }

    override fun update(entity: Dish): Dish {
        val entityId = entity.id ?: throw IllegalArgumentException("Cannot update entity without ID")
        dataStore[entityId] = entity
        return entity
    }

    override fun deleteById(id: Long): Boolean{
        return dataStore.remove(id) != null
    }
}