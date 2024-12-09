package com.example.prueba2javier_nicolas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

// Clase para la pantalla "CuartaVistaActivity".
class CuartaVistaActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId") // Ignora advertencia sobre IDs inflados.
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cuartavista) // Asocia el diseño "cuartavista" con esta actividad.

        // Configura el botón para volver a la actividad anterior.
        val botonVolver: ImageButton = findViewById(R.id.botonVolver)
        botonVolver.setOnClickListener {
            finish() // Cierra la actividad actual.
        }

        // Configura el botón del logo para ir a la pantalla de inicio de sesión.
        val logoButton: ImageButton = findViewById(R.id.tvLogo)
        logoButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java) // Crea el intento.
            startActivity(intent) // Inicia la actividad de inicio de sesión.
        }
    }
}
