package com.example.task2vsk2.activity

import User
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R
import com.example.task2vsk2.service.Assignment
import com.example.task2vsk2.service.AssignmentService
import com.example.task2vsk2.service.Course
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.service.GradeService
import com.example.task2vsk2.UserService

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

        studentSpinner = findViewById(R.id.studentSpinner)
        assignmentSpinner = findViewById(R.id.assignmentSpinner)
        etGrade = findViewById(R.id.etGrade)
        btnSetGrade = findViewById(R.id.btnSetGrade)

        val courseId = intent.getIntExtra("courseId", -1)
        if (courseId == -1) {
            Toast.makeText(this, "Course ID табылмады!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        selectedCourse = CourseService.getAllCourses().find { it.id == courseId } ?: run {
            Toast.makeText(this, "Курс табылмады!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        students = selectedCourse.studentIds.mapNotNull { id ->
            val user = UserService.getUserById(id)
            Log.d("STUDENT", "Қосылған студент: ${user?.username} (ID=${user?.id})")
            user
        }

        if (students.isEmpty()) {
            Toast.makeText(this, "Бұл курсқа студент тіркелмеген!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        assignments = AssignmentService.getAssignmentsByCourseId(courseId)

        if (assignments.isEmpty()) {
            Toast.makeText(this, "Курста задание жоқ!", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        studentSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, students.map { it.username })
        assignmentSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, assignments.map { it.title })

        btnSetGrade.setOnClickListener {
            val student = students[studentSpinner.selectedItemPosition]
            val assignment = assignments[assignmentSpinner.selectedItemPosition]
            val gradeValue = etGrade.text.toString().toIntOrNull()

            if (gradeValue != null && gradeValue in 0..100) {
                GradeService.setGrade(student.id, assignment.id, gradeValue)

                Log.d("GRADE", "Баға қойылды: ${student.username} -> ${assignment.title} = $gradeValue")

                Toast.makeText(this, "Баға қойылды ", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "0-100 аралығында нақты баға енгізіңіз!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
