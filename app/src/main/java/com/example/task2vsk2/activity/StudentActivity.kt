package com.example.task2vsk2.activity

import User
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.UserService



class StudentActivity : AppCompatActivity() {

    private lateinit var courseListView: ListView
    private lateinit var btnLogout: Button

    private lateinit var student: User
    private lateinit var studentCourses: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student)

        courseListView = findViewById(R.id.courseListView)
        btnLogout = findViewById(R.id.btnLogout)

        student = UserService.currentUser!!
        studentCourses = CourseService.getCoursesByStudentId(student.id)

        val courseTitles = studentCourses.map { it.title }
        courseListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courseTitles)

        courseListView.setOnItemClickListener { _, _, position, _ ->
            val intent = Intent(this, StudentCourseDetailActivity::class.java)
            intent.putExtra("courseId", studentCourses[position].id)
            startActivity(intent)
        }

        btnLogout.setOnClickListener {
            UserService.logout()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        studentCourses = CourseService.getCoursesByStudentId(student.id)
        val courseTitles = studentCourses.map { it.title }
        courseListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courseTitles)
    }
}
