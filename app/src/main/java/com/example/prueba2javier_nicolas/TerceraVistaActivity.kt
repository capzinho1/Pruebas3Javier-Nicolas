package com.example.prueba2javier_nicolas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

// Clase que representa la tercera vista de la aplicación
class TerceraVistaActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId") // Suprime advertencias relacionadas con IDs no encontrados en tiempo de compilación
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.terceravista) // Asocia el diseño terceravista.xml con esta actividad

        // Configura el botón "Volver" para regresar a la actividad anterior
        val botonVolver: ImageButton = findViewById(R.id.botonVolver)
        botonVolver.setOnClickListener {
            finish() // Finaliza la actividad actual
        }

        // Configura el botón del logo para redirigir a la actividad de inicio de sesión
        val logoButton: ImageButton = findViewById(R.id.tvLogo)
        logoButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java) // Intención para abrir LoginActivity
            startActivity(intent) // Inicia la actividad de inicio de sesión
        }
    }
}
