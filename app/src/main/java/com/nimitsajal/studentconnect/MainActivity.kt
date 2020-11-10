package com.nimitsajal.studentconnect

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var userName:String? = null
        var userEmail:String? = null
        var userPassword:String? = null
        var userUserName:String? = null
        var userPhone:String? = null
        var selectedPhotoUrl: Uri? = null

        toSignup_login.setOnClickListener {
            val intent = Intent(this, Sign_up::class.java)
            intent.putExtra("userName_signup", userName)
            intent.putExtra("userEmail_signup", userEmail)
            intent.putExtra("userPassword_signup", userPassword)
            intent.putExtra("userUserName_signup", userUserName)
            intent.putExtra("userPhone_signup", userPhone)
            intent.putExtra("dpImage_string", selectedPhotoUrl.toString())
            startActivity(intent)
        }
    }
}
