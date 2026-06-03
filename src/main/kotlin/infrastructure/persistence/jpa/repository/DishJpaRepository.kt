package infrastructure.persistence.jpa.repository

import infrastructure.persistence.jpa.entity.DishEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface DishJpaRepository : JpaRepository<DishEntity, Long> {
    fun findByName(name: String): DishEntity?

    @Query("SELECT d FROM DishEntity d WHERE LOWER(d.name) LIKE LOWER(CONCAT('%', :namePart, '%'))")
    fun findByNameContainingIgnoreCase(@Param("namePart") namePart: String): List<DishEntity>
}