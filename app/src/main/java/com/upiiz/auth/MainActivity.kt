package com.upiiz.auth

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val boton = findViewById<Button>(R.id.Ingresar)
        boton.setOnClickListener {
            iniciarSesion()
        }

        val botonR = findViewById<Button>(R.id.btnRegistrase)
        botonR.setOnClickListener {
            registrarUsuario()
        }

        val user = FirebaseAuth.getInstance().currentUser

        if (user != null) {
            val userId = user.uid
            val email = user.email
            Toast.makeText(this,"Sesion iniciada "+user.email,Toast.LENGTH_LONG).show()
            val intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

    }

    private fun registrarUsuario(){
        val user = findViewById<EditText>(R.id.editTextUser)
        val pass = findViewById<EditText>(R.id.editTextPassword)
        if(pass.text.isNotEmpty() and user.text.isNotEmpty()){
        Firebase.auth.createUserWithEmailAndPassword(user.text.toString(), pass.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        baseContext,
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT,
                    ).show()
                } else {
                    Toast.makeText(
                        baseContext,
                        "Ocurrio un error al registrase.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
        }
    }
    private fun iniciarSesion() {
        val user = findViewById<EditText>(R.id.editTextUser)
        val pass = findViewById<EditText>(R.id.editTextPassword)
        if(pass.text.isNotEmpty() and user.text.isNotEmpty()){
            FirebaseAuth.getInstance().signInWithEmailAndPassword(user.text.toString(), pass.text.toString())
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val user = FirebaseAuth.getInstance().currentUser
                        val intent = Intent(this,MenuActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }else{
            Toast.makeText(
                baseContext,
                "Rellene los campos.",
                Toast.LENGTH_SHORT,
            ).show()
        }

    }
}