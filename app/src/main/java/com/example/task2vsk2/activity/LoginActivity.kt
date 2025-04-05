package com.example.task2vsk2.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.task2vsk2.R

class LoginActivity : AppCompatActivity() {
    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        usernameEditText = findViewById(R.id.etUsername)
        passwordEditText = findViewById(R.id.etPassword)
        loginButton = findViewById(R.id.btnLogin)

        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            val user = UserService.login(username, password)

            if (user != null) {
                when (user.role) {
                    Role.ADMIN -> startActivity(Intent(this, AdminActivity::class.java))
                    Role.TEACHER -> startActivity(Intent(this, TeacherActivity::class.java))
                    Role.STUDENT -> startActivity(Intent(this, StudentActivity::class.java))
                }
            } else {
                Toast.makeText(this, "Қате логин немесе пароль!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
