import org.assertj.core.api.Assertions

@file:Connekt(
    baseUrl = "http://localhost:8080",
    envFile = "connekt.env.json"
)


GET("/greeting") {
    name = "Get Greeting (no params)"
    tags = listOf("greeting", "get")

    test {
        status(200)
        jsonPath("$.text").isString()
    }
}


POST("/greeting") {
    name = "Create User"
    tags = listOf("user", "post")

    jsonBody {
        "name" to "Ivan"
        "surname" to "Ivanov"
    }

    test {
        status(200)
        jsonPath("$.text").isEqualTo("Hello, Ivanov Ivan")
        jsonPath("$.id").isString()
        save("userId", response.jsonPath("$.id"))
    }
}


GET("/greeting") {
    name = "Get User by Query Param"
    tags = listOf("user", "get")

    params {
        "id" to "{{userId}}"
    }

    test {
        status(200)
        jsonPath("$.name").isEqualTo("Ivan")
        jsonPath("$.surname").isEqualTo("Ivanov")
    }
}


GET("/greeting/{{userId}}") {
    name = "Get User by Path"
    tags = listOf("user", "get")

    test {
        status(200)
        jsonPath("$.name").isEqualTo("Ivan")
        jsonPath("$.surname").isEqualTo("Ivanov")
    }
}


GET("/greeting") {
    name = "Get User with Invalid ID"
    tags = listOf("user", "get", "error")

    params {
        "id" to "00000000-0000-0000-0000-000000000000"
    }

    test {
        status(404)
    }
}