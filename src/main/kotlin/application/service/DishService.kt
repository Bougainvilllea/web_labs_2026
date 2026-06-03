package application.service

import domain.model.Dish
import domain.port.DishRepositoryPort
import domain.service.DishService
import org.springframework.stereotype.Service
import shared.exception.BusinessException

@Service
class DishService(
    private val dishRepository: DishRepositoryPort
) : DishService {

    override fun getDishById(dishIdentifier: Long): Dish {
        return dishRepository.findById(dishIdentifier)
            ?: throw BusinessException.DishNotFound(dishIdentifier)
    }

    override fun getAllDishes(nameFilter: String?): List<Dish> {
        val allDishes = dishRepository.findAll()

        return if (!nameFilter.isNullOrBlank()) {
            allDishes.filter { it.name.contains(nameFilter, ignoreCase = true) }
        } else {
            allDishes
        }
    }

    override fun findByName(dishName: String): Dish? {
        return dishRepository.findByName(dishName)
    }

    override fun createOrGetDish(dishToProcess: Dish): Pair<Dish, Boolean> {
        val existingDish = findByName(dishToProcess.name)

        if (existingDish != null) {
            return Pair(existingDish, false)
        }

        return try {
            Pair(dishRepository.create(dishToProcess), true)
        } catch (e: IllegalArgumentException) {
            throw BusinessException.DishValidationError(e.message ?: "Invalid dish data")
        }
    }

    override fun updateDish(dishToModify: Dish): Dish {
        val existingDish = dishRepository.findById(dishToModify.id!!)
            ?: throw BusinessException.DishNotFound(dishToModify.id!!)

        val dishWithSameName = findByName(dishToModify.name)
        if (dishWithSameName != null && dishWithSameName.id != dishToModify.id) {
            throw BusinessException.DishNameAlreadyExists(dishToModify.name)
        }

        return dishRepository.update(dishToModify)
    }

    override fun deleteDishById(dishIdentifier: Long): Boolean {
        val dishExists = dishRepository.findById(dishIdentifier)
            ?: throw BusinessException.DishNotFound(dishIdentifier)

        return dishRepository.deleteById(dishIdentifier)
    }
}