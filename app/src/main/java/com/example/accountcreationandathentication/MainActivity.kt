package com.example.accountcreationandathentication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.accountcreationandathentication.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //retrieve the details sent from the register activity using intent
        val userId = intent.getStringExtra("userId")
        val userEmail = intent.getStringExtra("emailId")

        binding.tvUserId.text = userId
        binding.tvUserEmail.text = userEmail

        binding.btnLogout.setOnClickListener {

            //log out
            FirebaseAuth.getInstance().signOut()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }


    }
}