package com.example.prueba2javier_nicolas

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton

class LoginActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val botonVolver: ImageButton = findViewById(R.id.botonVolver)
        botonVolver.setOnClickListener {
            finish()
        }
    }
}