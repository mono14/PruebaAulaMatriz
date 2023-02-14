package com.example.primeraclaseandroid.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.primeraclaseandroid.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private  lateinit var mAuth: FirebaseAuth
    private lateinit var dataBase: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mAuth = FirebaseAuth.getInstance()
        dataBase = FirebaseFirestore.getInstance()

        binding.btnCreateUser.setOnClickListener {
            createUser()
        }

    }

    private fun createUser(){
        val firstName = binding.txtName.text.toString()
        val lastName = binding.txtLastName.text.toString()
        val email = binding.txtEmail.text.toString()
        val password = binding.txtPassword.text.toString()

        if (firstName.isNotEmpty()&&lastName.isNotEmpty()&&email.isNotEmpty()&&password.isNotEmpty()){
            binding.loading.loading.visibility = View.VISIBLE

            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener{
                task ->
                binding.loading.loading.visibility = View.GONE

                if (task.isSuccessful){

                    val user: FirebaseUser? = mAuth.currentUser
                    user?.let { it1 -> verifyEmail(it1) }

                    user?.email?.let {email ->
                        dataBase.collection("users").document(email).set(
                            hashMapOf(
                            "nombre" to firstName,
                            "apellido" to lastName
                            )
                        )
                    }

                    Toast.makeText(
                        this, "Cuenta creada exitosamente.",
                        Toast.LENGTH_LONG
                    ).show()
                    updateUserInfoAndGoHome()

                } else {
                    Toast.makeText(
                        this,
                        "Error en el registro ",
                        Toast.LENGTH_LONG
                    ).show()
                }

            }.addOnFailureListener {
                Toast.makeText(
                    this,
                    "Error en el registro. ${it.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                this,
                "por favor ingrese todos los campos ",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun updateUserInfoAndGoHome() {
        // Nos vamos a la actividad home
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun verifyEmail(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this,
                        "Email " + user.getEmail(),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(
                        this,
                        "Error al verificar el correo ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }
}