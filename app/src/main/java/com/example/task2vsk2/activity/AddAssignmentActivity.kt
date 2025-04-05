package com.example.task2vsk2.activity

import User
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R
import com.example.task2vsk2.service.AssignmentService
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService

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

        // 🔧 UserService ішінен currentUser-ді аламыз
        teacher = UserService.currentUser!!
        teacherCourses = CourseService.getCoursesByTeacherId(teacher.id)

        courseSpinner = findViewById(R.id.courseSpinner)
        etTitle = findViewById(R.id.etAssignmentTitle)
        etDescription = findViewById(R.id.etAssignmentDesc)
        btnAdd = findViewById(R.id.btnCreateAssignment)

        val courseTitles = teacherCourses.map { it.title }
        courseSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseTitles)

        btnAdd.setOnClickListener {
            val title = etTitle.text.toString()
            val desc = etDescription.text.toString()
            val selectedCourseId = teacherCourses[courseSpinner.selectedItemPosition].id

            if (title.isNotEmpty() && desc.isNotEmpty()) {
                AssignmentService.addAssignment(title, desc, selectedCourseId)
                Toast.makeText(this, "Задание қосылды!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Барлық өрістерді толтырыңыз!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
