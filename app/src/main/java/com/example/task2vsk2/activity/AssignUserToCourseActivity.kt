package com.example.task2vsk2.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.R

class AssignUserToCourseActivity : AppCompatActivity() {
    private lateinit var userSpinner: Spinner
    private lateinit var courseSpinner: Spinner
    private lateinit var roleSpinner: Spinner
    private lateinit var btnAssign: Button

    private val users = UserService.getAllUsers()
    private val courses = CourseService.getAllCourses()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assign_user)

        userSpinner = findViewById(R.id.userSpinner)
        courseSpinner = findViewById(R.id.courseSpinner)
        roleSpinner = findViewById(R.id.roleSpinner)
        btnAssign = findViewById(R.id.btnAssign)

        val usernames = users.map { it.username }
        val courseNames = courses.map { it.title }
        val roles = listOf("STUDENT", "TEACHER")

        userSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, usernames)
        courseSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, courseNames)
        roleSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, roles)

        btnAssign.setOnClickListener {
            val user = users[userSpinner.selectedItemPosition]
            val course = courses[courseSpinner.selectedItemPosition]
            val role = Role.valueOf(roleSpinner.selectedItem.toString())

            CourseService.assignUserToCourse(user.id, course.id, role)

            Toast.makeText(this, "Қолданушы курсқа қосылды!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}
