package com.example.task2vsk2.activity

import User
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService


class TeacherActivity : AppCompatActivity() {

    private lateinit var courseListView: ListView
    private lateinit var btnAddAssignment: Button
    private lateinit var btnLogout: Button

    private lateinit var teacher: User
    private lateinit var teacherCourses: List<Course>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_teacher)

        courseListView = findViewById(R.id.courseListView)
        btnAddAssignment = findViewById(R.id.btnAddAssignment)
        btnLogout = findViewById(R.id.btnLogout)

        teacher = UserService.currentUser!!
        teacherCourses = CourseService.getCoursesByTeacherId(teacher.id)

        val courseNames = teacherCourses.map { it.title }
        courseListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courseNames)

        btnAddAssignment.setOnClickListener {
            startActivity(Intent(this, AddAssignmentActivity::class.java))
        }

        courseListView.setOnItemClickListener { _, _, position, _ ->
            val course = teacherCourses[position]
            val intent = Intent(this, GradeStudentsActivity::class.java)
            intent.putExtra("courseId", course.id)
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
        teacherCourses = CourseService.getCoursesByTeacherId(teacher.id)
        val courseNames = teacherCourses.map { it.title }
        courseListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, courseNames)
    }
}
