package com.example.task2vsk2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R
import com.example.task2vsk2.service.CourseService

class AdminActivity : AppCompatActivity() {

    private lateinit var courseListView: ListView
    private lateinit var btnAddCourse: Button
    private lateinit var btnAssignUser: Button
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        courseListView = findViewById(R.id.courseListView)
        btnAddCourse = findViewById(R.id.btnAddCourse)
        btnAssignUser = findViewById(R.id.btnAssignUser)
        btnLogout = findViewById(R.id.btnLogout)

        updateCourseList()

        btnAddCourse.setOnClickListener {
            startActivity(Intent(this, AddCourseActivity::class.java))
        }

        btnAssignUser.setOnClickListener {
            startActivity(Intent(this, AssignUserToCourseActivity::class.java))
        }

        btnLogout.setOnClickListener {
            UserService.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun updateCourseList() {
        val courses = CourseService.getAllCourses()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courses.map { it.title })
        courseListView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateCourseList()
    }
}
