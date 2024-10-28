package com.example.prueba2javier_nicolas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

class CuartaVistaActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuartavista)

        val botonVolver: ImageButton = findViewById(R.id.botonVolver)
        botonVolver.setOnClickListener {
            finish()
        }
        val logoButton: ImageButton = findViewById(R.id.tvLogo)
        logoButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }

}
