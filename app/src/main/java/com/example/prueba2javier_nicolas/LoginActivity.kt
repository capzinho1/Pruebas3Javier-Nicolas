package com.example.prueba2javier_nicolas

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase

// Clase para la pantalla de inicio de sesión.
class LoginActivity : AppCompatActivity() {

    // Campos de texto y referencia a la base de datos.
    private lateinit var etEmail: TextInputEditText
    private lateinit var etPassword: TextInputEditText
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login) // Asocia el diseño "login" con esta actividad.

        // Inicializa los campos de entrada y la base de datos.
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        database = FirebaseDatabase.getInstance()

        // Configura el botón "Volver" para cerrar esta actividad.
        val botonVolver: ImageButton = findViewById(R.id.botonVolver)
        botonVolver.setOnClickListener {
            finish() // Cierra la actividad actual.
        }

        // Configura el botón "Login" para procesar la autenticación.
        val btnLogin = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnLogin)
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString()
            val password = etPassword.text.toString()

            // Valida los campos y el formato del correo electrónico.
            if (email.isNotEmpty() && password.isNotEmpty()) {
                if (isValidEmail(email)) {
                    saveDataToFirebase(email, password) // Guarda los datos en Firebase.
                } else {
                    showInvalidEmailAlert() // Muestra una alerta si el correo no es válido.
                }
            } else {
                // Muestra un mensaje si los campos están vacíos.
                Toast.makeText(this, "Por favor, ingresa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configura el botón "Regístrate" para abrir la actividad de registro.
        val btnRegister = findViewById<com.google.android.material.button.MaterialButton>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, RegistroActivity::class.java)
            startActivity(intent) // Inicia la actividad de registro.
        }
    }

    // Verifica si un correo electrónico tiene un formato válido.
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Guarda los datos de usuario en Firebase.
    private fun saveDataToFirebase(email: String, password: String) {
        val databaseReference = database.reference.child("Usuarios") // Nodo "Usuarios" en Firebase.
        val userId = databaseReference.push().key // Genera una clave única para el usuario.

        // Datos del usuario que se guardarán.
        val userMap = mapOf(
            "email" to email,
            "password" to password
        )

        // Verifica que la clave generada no sea nula antes de guardar.
        userId?.let {
            databaseReference.child(it).setValue(userMap)
                .addOnSuccessListener {
                    // Notifica el éxito y redirige a MainActivity.
                    Toast.makeText(this, "Usuario guardado exitosamente", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish() // Cierra esta actividad.
                }
                .addOnFailureListener { exception ->
                    // Muestra un error si ocurre un problema al guardar.
                    Toast.makeText(this, "Error al guardar: ${exception.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }

    // Muestra un mensaje de alerta si el correo no es válido.
    private fun showInvalidEmailAlert() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Correo inválido")
            .setMessage("Por favor, ingresa un correo electrónico válido")
            .setIcon(R.drawable.ic_error) // Icono de error.
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss() // Cierra el diálogo.
            }
            .show()
    }
}
