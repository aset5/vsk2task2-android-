package com.example.task2vsk2.service

data class Assignment(
    val id: Int,
    val title: String,
    val description: String,
    val courseId: Int
)

object AssignmentService {
    private val assignments = mutableListOf<Assignment>()
    private var nextId = 1

    fun addAssignment(title: String, description: String, courseId: Int) {
        assignments.add(Assignment(nextId++, title, description, courseId))
    }

    fun getAssignmentsByCourseId(courseId: Int): List<Assignment> {
        return assignments.filter { it.courseId == courseId }
    }
}
