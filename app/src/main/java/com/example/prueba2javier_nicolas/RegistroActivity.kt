package com.example.prueba2javier_nicolas

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

// Clase que representa la actividad de registro de usuarios
class RegistroActivity : AppCompatActivity() {

    // Declaración de campos y variables necesarias
    private lateinit var etEmail: EditText // Campo de texto para el correo
    private lateinit var etPassword: EditText // Campo de texto para la contraseña
    private lateinit var etUsername: EditText // Campo de texto para el nombre de usuario
    private lateinit var ivProfileImage: ImageView // Imagen de perfil seleccionada
    private lateinit var database: FirebaseDatabase // Referencia a la base de datos Firebase
    private lateinit var storage: FirebaseStorage // Referencia al almacenamiento Firebase
    private var selectedImageUri: Uri? = null // URI de la imagen seleccionada

    // Método que se ejecuta al crear la actividad
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro) // Asocia el diseño XML con esta actividad

        // Inicializa los campos de texto y vistas
        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)
        etUsername = findViewById(R.id.etUsername)
        ivProfileImage = findViewById(R.id.ivProfileImage)

        val btnRegister: Button = findViewById(R.id.btnRegister) // Botón para registrar usuario
        val btnBack: ImageButton = findViewById(R.id.botonVolver) // Botón para volver a la actividad anterior

        // Inicializa las referencias de Firebase
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()

        // Configuración para seleccionar una imagen de perfil
        val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                selectedImageUri = uri // Guarda la URI seleccionada
                ivProfileImage.setImageURI(uri) // Muestra la imagen seleccionada en la vista
            }
        }

        // Evento de clic para seleccionar la imagen de perfil
        ivProfileImage.setOnClickListener {
            imagePickerLauncher.launch("image/*") // Abre un selector de contenido para imágenes
        }

        // Evento de clic para registrar al usuario
        btnRegister.setOnClickListener {
            val email = etEmail.text.toString().trim() // Obtiene el correo ingresado
            val password = etPassword.text.toString() // Obtiene la contraseña ingresada
            val username = etUsername.text.toString().trim() // Obtiene el nombre de usuario ingresado

            // Valida las entradas del usuario
            if (validateInputs(email, password, username)) {
                registerUser(email, password, username) // Llama a la función para registrar al usuario
            }
        }

        // Evento de clic para volver a la actividad anterior
        btnBack.setOnClickListener {
            finish() // Finaliza la actividad actual
        }
    }

    // Función para validar las entradas del usuario
    private fun validateInputs(email: String, password: String, username: String): Boolean {
        if (!isValidEmail(email)) {
            // Muestra un mensaje si el correo no es válido
            Toast.makeText(this, "El correo no es válido.", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!isValidPassword(password)) {
            // Muestra un mensaje si la contraseña no cumple los requisitos
            Toast.makeText(
                this,
                "Contraseña debe tener al menos 8 caracteres, una mayúscula, una minúscula y un símbolo.",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        if (username.isEmpty()) {
            // Muestra un mensaje si el nombre de usuario está vacío
            Toast.makeText(this, "El nombre de usuario no puede estar vacío.", Toast.LENGTH_SHORT).show()
            return false
        }
        return true // Retorna true si todas las validaciones son correctas
    }

    // Valida el formato del correo electrónico
    private fun isValidEmail(email: String): Boolean {
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}"
        return email.matches(emailPattern.toRegex()) // Retorna true si el correo cumple el patrón
    }

    // Valida el formato de la contraseña
    private fun isValidPassword(password: String): Boolean {
        val passwordPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?]).{8,}\$"
        return password.matches(passwordPattern.toRegex()) // Retorna true si la contraseña cumple el patrón
    }

    // Registra al usuario en Firebase
    private fun registerUser(email: String, password: String, username: String) {
        val databaseReference = database.reference.child("UsuariosRegistrados") // Nodo en la base de datos
        val userId = databaseReference.push().key ?: return // Genera una clave única para el usuario

        // Mapa con la información del usuario
        val userMap = mutableMapOf(
            "email" to email,
            "password" to password,
            "username" to username
        )

        // Si se seleccionó una imagen, la sube al almacenamiento
        if (selectedImageUri != null) {
            val storageReference = storage.reference.child("profile_images/$userId.jpg") // Ruta en el almacenamiento
            storageReference.putFile(selectedImageUri!!)
                .addOnSuccessListener {
                    storageReference.downloadUrl.addOnSuccessListener { uri ->
                        userMap["profileImageUrl"] = uri.toString() // Agrega la URL de la imagen al mapa
                        saveUserToDatabase(userId, userMap) // Guarda al usuario en la base de datos
                    }
                }
                .addOnFailureListener {
                    // Muestra un mensaje si ocurre un error al subir la imagen
                    Toast.makeText(this, "Error al subir la imagen", Toast.LENGTH_SHORT).show()
                }
        } else {
            // Si no hay imagen, guarda al usuario directamente
            saveUserToDatabase(userId, userMap)
        }
    }

    // Guarda al usuario en la base de datos Firebase
    private fun saveUserToDatabase(userId: String, userMap: Map<String, String>) {
        val databaseReference = database.reference.child("UsuariosRegistrados")
        databaseReference.child(userId).setValue(userMap)
            .addOnSuccessListener {
                // Muestra un mensaje de éxito y finaliza la actividad
                Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()
                finish()
            }
            .addOnFailureListener { e ->
                // Muestra un mensaje si ocurre un error al guardar
                Toast.makeText(this, "Error al registrar usuario: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
