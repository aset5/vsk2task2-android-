package com.example.task2vsk2.activity

import User
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.service.Assignment
import com.example.task2vsk2.service.AssignmentService
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.service.GradeService
import com.example.task2vsk2.R

class GradeStudentsActivity : AppCompatActivity() {
    private lateinit var studentSpinner: Spinner
    private lateinit var assignmentSpinner: Spinner
    private lateinit var etGrade: EditText
    private lateinit var btnSetGrade: Button

    private lateinit var selectedCourse: Course
    private lateinit var students: List<User>
    private lateinit var assignments: List<Assignment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grade_students)

        val courseId = intent.getIntExtra("courseId", -1)
        selectedCourse = CourseService.getAllCourses().find { it.id == courseId }!!

        studentSpinner = findViewById(R.id.studentSpinner)
        assignmentSpinner = findViewById(R.id.assignmentSpinner)
        etGrade = findViewById(R.id.etGrade)
        btnSetGrade = findViewById(R.id.btnSetGrade)

        students = selectedCourse.studentIds.mapNotNull { id -> UserService.getUserById(id) }
        assignments = AssignmentService.getAssignmentsByCourseId(courseId)

        studentSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, students.map { it.username })
        assignmentSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, assignments.map { it.title })

        btnSetGrade.setOnClickListener {
            val student = students[studentSpinner.selectedItemPosition]
            val assignment = assignments[assignmentSpinner.selectedItemPosition]
            val gradeValue = etGrade.text.toString().toIntOrNull()

            if (gradeValue != null && gradeValue in 0..100) {
                GradeService.setGrade(student.id, assignment.id, gradeValue)
                Toast.makeText(this, "Оценка қойылды!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "0-100 аралығында баға енгізіңіз!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
