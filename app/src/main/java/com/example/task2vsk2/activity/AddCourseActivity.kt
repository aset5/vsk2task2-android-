package com.example.task2vsk2.activity

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.service.CourseService
import com.example.task2vsk2.R

class AddCourseActivity : AppCompatActivity() {
    private lateinit var etCourseTitle: EditText
    private lateinit var etCourseDescription: EditText
    private lateinit var btnCreateCourse: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_course)

        etCourseTitle = findViewById(R.id.etCourseTitle)
        etCourseDescription = findViewById(R.id.etCourseDescription)
        btnCreateCourse = findViewById(R.id.btnCreateCourse)

        btnCreateCourse.setOnClickListener {
            val title = etCourseTitle.text.toString()
            val description = etCourseDescription.text.toString()

            if (title.isNotEmpty() && description.isNotEmpty()) {
                CourseService.createCourse(title, description)
                Toast.makeText(this, "Курс қосылды!", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Барлық өрістерді толтырыңыз!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
