package com.example.task2vsk2.service

import Role

data class Course(
    val id: Int,
    val title: String,
    val description: String,
    var teacherId: Int? = null,
    val studentIds: MutableList<Int> = mutableListOf()
)

object CourseService {
    private val courses = mutableListOf<Course>()
    private var nextId = 1

    fun createCourse(title: String, description: String) {
        val course = Course(id = nextId++, title = title, description = description)
        courses.add(course)
    }

    fun getAllCourses(): List<Course> {
        return courses
    }

    fun assignUserToCourse(userId: Int, courseId: Int, role: Role) {
        val course = courses.find { it.id == courseId } ?: return
        when (role) {
            Role.STUDENT -> course.studentIds.add(userId)
            Role.TEACHER -> course.teacherId = userId
            else -> {}
        }
    }

    fun getCoursesByTeacherId(teacherId: Int): List<Course> {
        return courses.filter { it.teacherId == teacherId }
    }

    fun getCoursesByStudentId(studentId: Int): List<Course> {
        return courses.filter { it.studentIds.contains(studentId) }
    }
}
