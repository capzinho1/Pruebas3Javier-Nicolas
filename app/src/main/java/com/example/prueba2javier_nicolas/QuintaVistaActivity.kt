package com.example.prueba2javier_nicolas

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity

// Clase que representa la quinta vista de la aplicación, asociada a la interfaz "quintavista"
class QuintaVistaActivity : AppCompatActivity() {

    // Método que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.quintavista) // Asocia el diseño "quintavista" con esta actividad

        // Configuración del botón "Volver" para regresar a la vista anterior
        val botonVolver: ImageButton = findViewById(R.id.botonVolver)
        botonVolver.setOnClickListener {
            // Finaliza la actividad actual, volviendo a la actividad anterior en la pila
            finish()
        }

        // Configuración del botón del logo para redirigir al inicio de sesión
        val logoButton: ImageButton = findViewById(R.id.tvLogo)
        logoButton.setOnClickListener {
            // Crea un Intent para navegar a la actividad LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent) // Inicia la actividad de inicio de sesión
        }
    }
}
