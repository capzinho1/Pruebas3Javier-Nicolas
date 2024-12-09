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
import com.example.prueba2javier_nicolas.databinding.ActivityMainBinding
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
// Clase principal de la aplicación donde se manejan la entrada de parámetros y la navegación entre diferentes vistas
class MainActivity : AppCompatActivity() {

    // Declaración de campos para la entrada de los parámetros
    private lateinit var editTextDato1: EditText
    private lateinit var editTextDato2: EditText
    private lateinit var editTextDato3: EditText
    private lateinit var editTextDato4: EditText

    // Inicialización de la base de datos de Firebase
    private lateinit var database: FirebaseDatabase

    // Uso de viewBinding para una interacción más eficiente con las vistas del layout
    private lateinit var binding: ActivityMainBinding

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Asocia el layout con el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa la referencia a la base de datos de Firebase
        database = FirebaseDatabase.getInstance()

        // Configuración de botones para navegación entre vistas
        val logoButton: ImageButton = findViewById(R.id.tvLogo)
        logoButton.setOnClickListener {
            // Redirige a la actividad de inicio de sesión
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        val homeButton: ImageButton = findViewById(R.id.home)
        homeButton.setOnClickListener {
            // Redirige a la actividad principal
            val intent = Intent(this, CuartaVistaActivity::class.java)
            startActivity(intent)
        }

        val guiaButton: ImageButton = findViewById(R.id.guia)
        guiaButton.setOnClickListener {
            // Redirige a la actividad de guía
            val intent = Intent(this, QuintaVistaActivity::class.java)
            startActivity(intent)
        }

        val statsButton: ImageButton = findViewById(R.id.stats)
        statsButton.setOnClickListener {
            // Redirige a la actividad de estadísticas
            val intent = Intent(this, TerceraVistaActivity::class.java)
            startActivity(intent)
        }

        // Asocia los campos de entrada de texto para capturar parámetros
        editTextDato1 = findViewById(R.id.dato1)
        editTextDato2 = findViewById(R.id.dato2)
        editTextDato3 = findViewById(R.id.dato3)
        editTextDato4 = findViewById(R.id.dato4)

        // Configura el botón para procesar los parámetros ingresados
        val buttonProcesar: Button = findViewById(R.id.buttonProcesar)
        buttonProcesar.setOnClickListener {
            try {
                // Intenta convertir los valores ingresados en los campos a números enteros
                val parametro1 = editTextDato1.text.toString().toInt()
                val parametro2 = editTextDato2.text.toString().toInt()
                val parametro3 = editTextDato3.text.toString().toInt()
                val parametro4 = editTextDato4.text.toString().toInt()

                // Llama a la función para guardar los parámetros en Firebase
                saveParametersToFirebase(parametro1, parametro2, parametro3, parametro4)

                // Muestra un mensaje indicando éxito en la operación
                showCustomToast("Parámetros establecidos: $parametro1, $parametro2, $parametro3, $parametro4")
            } catch (e: NumberFormatException) {
                // Si ocurre un error al convertir a números, muestra un mensaje de advertencia
                showCustomToast("Error: Por favor, ingresa solo números")
            }
        }

        // Ajusta los márgenes de las vistas para respetar las barras del sistema
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    // Función para guardar los parámetros en Firebase
    private fun saveParametersToFirebase(param1: Int, param2: Int, param3: Int, param4: Int) {
        // Define el nodo "Parametros" en la base de datos
        val databaseReference = database.reference.child("Parametros")

        // Genera una clave única para los parámetros
        val parametersKey = databaseReference.push().key

        // Crea un mapa con los valores de los parámetros
        val parametersMap = mapOf(
            "Parametro 1" to param1,
            "Parametro 2" to param2,
            "Parametro 3" to param3,
            "Parametro 4" to param4
        )

        // Guarda los datos en la base de datos si la clave es válida
        parametersKey?.let {
            databaseReference.child(it).setValue(parametersMap)
                .addOnSuccessListener {
                    // Muestra un mensaje de éxito al guardar los datos
                    showCustomToast("Datos guardados en Firebase exitosamente")
                }
                .addOnFailureListener { exception ->
                    // Muestra un mensaje de error en caso de fallo
                    showCustomToast("Error al guardar en Firebase: ${exception.message}")
                }
        }
    }

    // Función para mostrar un Toast personalizado con un mensaje específico
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
