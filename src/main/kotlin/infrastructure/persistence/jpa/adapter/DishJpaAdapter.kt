package infrastructure.persistence.jpa.adapter

import domain.model.Dish
import domain.port.DishRepositoryPort
import infrastructure.persistence.jpa.repository.DishJpaRepository
import infrastructure.persistence.jpa.entity.DishEntity
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("db")
class DishJpaAdapter(
    private val dishJpaRepo: DishJpaRepository
) : DishRepositoryPort {

    override fun findAll(): List<Dish> {
        return dishJpaRepo.findAll().map { it.toDomain() }
    }

    override fun findById(id: Long): Dish? {
        return dishJpaRepo.findById(id).map { it.toDomain() }.orElse(null)
    }

    override fun create(entity: Dish): Dish {
        val dishEntity = DishEntity.fromDomain(entity)
        val savedEntity = dishJpaRepo.save(dishEntity)
        return savedEntity.toDomain()
    }

    override fun update(entity: Dish): Dish {
        val existingEntity = dishJpaRepo.findById(entity.id!!)
            .orElseThrow { IllegalArgumentException("Dish with id ${entity.id} not found") }

        val updatedEntity = DishEntity.fromDomain(entity)
        val savedEntity = dishJpaRepo.save(updatedEntity)
        return savedEntity.toDomain()
    }

    override fun deleteById(id: Long): Boolean {
        return if (dishJpaRepo.existsById(id)) {
            dishJpaRepo.deleteById(id)
            true
        } else {
            false
        }
    }

    override fun findByName(name: String): Dish? {
        return dishJpaRepo.findByName(name)?.toDomain()
    }
}