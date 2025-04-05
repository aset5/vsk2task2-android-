package com.example.task2vsk2.activity

import User
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.service.Assignment
import com.example.task2vsk2.service.AssignmentService
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.service.GradeService
import com.example.task2vsk2.R

class StudentCourseDetailActivity : AppCompatActivity() {
    private lateinit var assignmentListView: ListView
    private lateinit var courseTitleTextView: TextView
    private lateinit var student: User
    private lateinit var assignments: List<Assignment>
    private lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_student_course_detail)

        assignmentListView = findViewById(R.id.assignmentListView)
        courseTitleTextView = findViewById(R.id.tvCourseTitle)

        student = UserService.currentUser!!
        val courseId = intent.getIntExtra("courseId", -1)
        course = CourseService.getAllCourses().find { it.id == courseId }!!

        courseTitleTextView.text = course.title
        assignments = AssignmentService.getAssignmentsByCourseId(course.id)

        val assignmentInfo = assignments.map { assignment ->
            val grade = GradeService.getGrade(student.id, assignment.id)?.grade
            "${assignment.title} - ${grade ?: "Баға қойылмаған"}"
        }

        assignmentListView.adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, assignmentInfo)
    }
}
