package com.example.task2vsk2.activity

import User
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R
import com.example.task2vsk2.service.AssignmentService
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.UserService

class AddAssignmentActivity : AppCompatActivity() {
    private lateinit var courseSpinner: Spinner
    private lateinit var etTitle: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnAdd: Button

    private lateinit var teacher: User
    private lateinit var teacherCourses: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_assignment)

        courseSpinner = findViewById(R.id.courseSpinner)
        etTitle = findViewById(R.id.etAssignmentTitle)
        etDescription = findViewById(R.id.etAssignmentDesc)
        btnAdd = findViewById(R.id.btnCreateAssignment)

        teacher = UserService.currentUser ?: run {
            Toast.makeText(this, "Пайдаланушы табылмады!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        teacherCourses = CourseService.getCoursesByTeacherId(teacher.id)

        if (teacherCourses.isEmpty()) {
            Toast.makeText(this, "Сізге бекітілген курс жоқ!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val courseTitles = teacherCourses.map { it.title }
        courseSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseTitles)

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString().trim()
            val description = etDescription.text.toString().trim()
            val selectedCourseId = teacherCourses[courseSpinner.selectedItemPosition].id

            if (title.isNotEmpty() && description.isNotEmpty()) {
                AssignmentService.addAssignment(title, description, selectedCourseId)
                Toast.makeText(this, "Задание сәтті қосылды!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Барлық өрістерді толтырыңыз!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
