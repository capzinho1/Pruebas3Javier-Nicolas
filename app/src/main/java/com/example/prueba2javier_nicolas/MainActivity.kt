package com.example.prueba2javier_nicolas

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.ImageButton

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var editTextDato1: EditText
    private lateinit var editTextDato2: EditText
    private lateinit var editTextDato3: EditText
    private lateinit var editTextDato4: EditText

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val logoButton: ImageButton = findViewById(R.id.tvLogo)
        logoButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
        val homeButton: ImageButton = findViewById(R.id.home)

        homeButton.setOnClickListener {
            val intent = Intent(this, CuartaVistaActivity::class.java)
            startActivity(intent)
        }

        val guiaButton: ImageButton = findViewById(R.id.guia)

        guiaButton.setOnClickListener {
            val intent = Intent(this, QuintaVistaActivity::class.java)
            startActivity(intent)
        }

        val statsButton: ImageButton = findViewById(R.id.stats)

        statsButton.setOnClickListener {
            val intent = Intent(this, TerceraVistaActivity::class.java)
            startActivity(intent)
        }

        editTextDato1 = findViewById(R.id.dato1)
        editTextDato2 = findViewById(R.id.dato2)
        editTextDato3 = findViewById(R.id.dato3)
        editTextDato4 = findViewById(R.id.dato4)

        val buttonProcesar: Button = findViewById(R.id.buttonProcesar)
        buttonProcesar.setOnClickListener {
            try {
                val parametro1 = editTextDato1.text.toString().toInt()
                val parametro2 = editTextDato2.text.toString().toInt()
                val parametro3 = editTextDato3.text.toString().toInt()
                val parametro4 = editTextDato4.text.toString().toInt()

                showCustomToast("Parámetros establecidos: $parametro1, $parametro2, $parametro3, $parametro4")
            } catch (e: NumberFormatException) {
                showCustomToast("Error: Por favor, ingresa solo números")
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun showCustomToast(message: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))

        val text: TextView = layout.findViewById(R.id.custom_toast_text)
        text.text = message

        with(Toast(applicationContext)) {
            duration = Toast.LENGTH_LONG
            view = layout
            show()
        }
    }
}
