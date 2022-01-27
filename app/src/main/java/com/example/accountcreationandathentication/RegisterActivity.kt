package com.example.accountcreationandathentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.Toast
import com.example.accountcreationandathentication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class RegisterActivity : AppCompatActivity() {
    private lateinit var myAuth: FirebaseAuth
    lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.tvLogin.setOnClickListener {

            onBackPressed()
//            val intent = Intent(this, LoginActivity::class.java)
//            startActivity(intent)
        }

        binding.btnRegister.setOnClickListener {

            when {
                TextUtils.isEmpty(binding.etRegisterEmail.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter email", Toast.LENGTH_SHORT).show()
                }

                TextUtils.isEmpty(
                    binding.etRegisterPassword.text.toString().trim { it <= ' ' }) -> {
                    Toast.makeText(this, "Please enter password", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val email = binding.etRegisterEmail.text.toString().trim { it <= ' ' }
                    val password = binding.etRegisterPassword.text.toString().trim { it <= ' ' }
                    //create an instance and register a user with email and password
                    myAuth = FirebaseAuth.getInstance()
                    myAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                //firebase registered user
                                val firebaseUser: FirebaseUser? = task!!.result!!.user
                                Toast.makeText(
                                    this, "Registration successful",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //Send the user to the main activity or the welcome
                                // activity in case of a real app

                                val intent = Intent(this, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or (Intent.FLAG_ACTIVITY_CLEAR_TASK)
                                intent.putExtra("userId", firebaseUser?.uid)
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
