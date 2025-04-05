package com.example.task2vsk2.service

data class Grade(
    val studentId: Int,
    val assignmentId: Int,
    val grade: Int
)

object GradeService {
    private val grades = mutableListOf<Grade>()

    fun setGrade(studentId: Int, assignmentId: Int, grade: Int) {
        val existing = grades.find { it.studentId == studentId && it.assignmentId == assignmentId }
        if (existing != null) {
            grades.remove(existing)
        }
        grades.add(Grade(studentId, assignmentId, grade))
    }

    fun getGrade(studentId: Int, assignmentId: Int): Grade? {
        return grades.find { it.studentId == studentId && it.assignmentId == assignmentId }
    }

    fun getGradesByStudent(studentId: Int): List<Grade> {
        return grades.filter { it.studentId == studentId }
    }
}
