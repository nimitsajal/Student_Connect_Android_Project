package com.nimitsajal.studentconnect

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val auth = FirebaseAuth.getInstance()
        if(auth.currentUser != null){
            Toast.makeText(this,"Welcome Student", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, mainFeed::class.java)
            startActivity(intent)
        }

        toSignup_login.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            startActivity(intent)
        }

        btnLogin_login.setOnClickListener{
            btnLogin_login.isEnabled = false
            val userEmail = etEmail_login.text.toString()
            val userPassword = etPassword_login.text.toString()
            if(userEmail.isBlank())
            {
                Toast.makeText(this,"Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(userPassword.isBlank()) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Log.d("login", "Going to database")
            auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener {
                btnLogin_login.isEnabled = true
                Log.d("login", "Went to database")
                if(it.isSuccessful) {
                    Toast.makeText(this, "Logging In", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, mainFeed::class.java)
                    startActivity(intent)
                    finish()
                }
                else
                {
                    Toast.makeText(this,"${it.exception}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
