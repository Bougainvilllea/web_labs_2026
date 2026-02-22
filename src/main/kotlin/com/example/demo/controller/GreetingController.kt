package com.example.demo.controller

import com.example.demo.dto.GreetingMain
import com.example.demo.dto.GreetingUser
import com.example.demo.dto.UserData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.UUID

@RestController
@RequestMapping("/greeting")
class GreetingController {

    private val users = mutableMapOf<UUID, UserData>()

    @GetMapping
    fun getGreeting(@RequestParam(required = false) id: UUID?): ResponseEntity<Any> {
        return if (id == null) {
            ResponseEntity.ok(GreetingMain("Hello World"))
        } else {
            val user = users[id]
            if (user != null) {
                ResponseEntity.ok(user)
            } else {
                ResponseEntity.notFound().build()
            }
        }
    }

    @PostMapping
    fun createUser(@RequestBody userData: UserData): ResponseEntity<GreetingUser> {
        val id = UUID.randomUUID()
        users[id] = userData

        val greeting = GreetingUser(
            text = "Hello, ${userData.surname} ${userData.name}",
            id = id
        )
        return ResponseEntity.ok(greeting)
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: UUID): ResponseEntity<UserData> {
        val user = users[id]
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }
}