package domain.port
import domain.model.Dish

interface DishRepositoryPort{
    fun findByName(name: String): Dish?
    fun findAll(): List<Dish>
    fun findById(id: Long): Dish?
    fun update(entity: Dish): Dish
    fun deleteById(id: Long): Boolean
    fun create(entity: Dish): Dish
}