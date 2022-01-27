package com.example.accountcreationandathentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.accountcreationandathentication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var myAuth: FirebaseAuth
    lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvRegister.setOnClickListener {

            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {

            when {
                TextUtils.isEmpty(binding.etLoginEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(
                    binding.etLoginPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email = binding.etLoginEmail.text.toString().trim { it <= ' ' }
                    val password = binding.etLoginPassword.text.toString().trim { it <= ' ' }
                    //login user with the provided email and password
                    myAuth = FirebaseAuth.getInstance()
                    myAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                //firebase logged in user
                               // val firebaseUser: FirebaseUser? = task!!.result!!.user
                                Toast.makeText(
                                    this, "login successful successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //Send the user to the main activity or the welcome
                                // activity in case of a real app

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or (Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.putExtra("userId", myAuth.currentUser?.uid)
                                intent.putExtra("emailId", email)
                                startActivity(intent)
                                finish()

                            } else {

                                //if the registration was not successful, show an error
                                Toast.makeText(
                                    this, task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }

                }


            }


        }


    }
}