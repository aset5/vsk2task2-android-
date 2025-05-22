package com.example.task2vsk2

import User

object UserService {
    private val users = mutableListOf(
        User(1, "Ers", "qwerty", Role.ADMIN),
        User(2, "Serik", "Serik1", Role.TEACHER),
        User(3, "Damir", "Dami7", Role.STUDENT)
    )

    var currentUser: User? = null

    fun login(username: String, password: String): User? {
        val user = users.find { it.username == username && it.password == password }
        if (user != null) currentUser = user
        return user
    }

    fun logout() {
        currentUser = null
    }

    fun getAllUsers(): List<User> = users

    fun getUserById(id: Int): User? = users.find { it.id == id }
}
