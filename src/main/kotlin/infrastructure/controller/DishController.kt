package infrastructure.controller

import domain.model.Dish
import domain.service.DishService
import org.example.example.infrastructure.dto.requests.dish.DishCreateRequest
import org.example.example.infrastructure.dto.requests.dish.DishUpdateRequest
import org.example.example.infrastructure.dto.response.DishResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import shared.mapper.DishMapper


@RestController
@RequestMapping("/api/v1/dishes")
class DishController(
    private val dishService: DishService,
    private val dishMapper: DishMapper
) {

    @GetMapping
    fun retrieveAllDishes(@RequestParam(required = false) namePart: String?): ResponseEntity<List<DishResponse>> {
        val domainDishes: List<Dish> = dishService.getAllDishes(namePart)
        val responseData: List<DishResponse> = domainDishes.map { dishMapper.toResponse(it) }
        return ResponseEntity.ok(responseData)
    }

    @PostMapping
    fun addNewDish(@RequestBody createRequest: DishCreateRequest): ResponseEntity<DishResponse> {
        val dishFromRequest = dishMapper.toDomain(createRequest)
        val (savedDish, isNew) = dishService.createOrGetDish(dishFromRequest)
        val responseData = dishMapper.toResponse(savedDish)

        val httpStatus = if (isNew) HttpStatus.CREATED else HttpStatus.OK
        return ResponseEntity.status(httpStatus).body(responseData)
    }

    @GetMapping("/{id}")
    fun fetchDishById(@PathVariable id: Long): ResponseEntity<DishResponse> {
        val dish = dishService.getDishById(id)
        val responseData = dishMapper.toResponse(dish)
        return ResponseEntity.ok(responseData)
    }

    @PutMapping("/{id}")
    fun updateDishDetails(@PathVariable id: Long, @RequestBody updateRequest: DishUpdateRequest): ResponseEntity<DishResponse> {
        val dish = dishMapper.toDomain(updateRequest).copy(id = id)
        val updatedDish = dishService.updateDish(dish)
        val responseData = dishMapper.toResponse(updatedDish)
        return ResponseEntity.ok(responseData)
    }

    @DeleteMapping("/{id}")
    fun deleteDishFromSystem(@PathVariable id: Long): ResponseEntity<Unit> {
        dishService.deleteDishById(id)
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    }
}