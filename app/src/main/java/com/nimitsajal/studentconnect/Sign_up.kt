package com.nimitsajal.studentconnect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_sign_up.*

class Sign_up : AppCompatActivity() {

    var selectedPhotoUrl: Uri? = null
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        toLoginPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnDP.setOnClickListener {
            photoPicker()
        }

        btnContinueToClgDetails.setOnClickListener {
            goToCollegeDetails()
        }

//        btnContinueToClgDetails.setOnClickListener {
//            performRegister()
//        }
    }

    private fun goToCollegeDetails(){
        val userName = etName_signup.text.toString()
        val userEmail = etEmail_signup.text.toString()
        val userPassword = etPassword_signup.text.toString()
        val userUserName = etUserName_signup.text.toString()
        val userPhone = etPhone_signup.text.toString()

        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userUserName.isEmpty() || userPhone.isEmpty()){
            Toast.makeText(this, "Don't leave any fields blank!", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent(this, collegeDetailsDatabase::class.java)
        intent.putExtra("userName_signup", userName)
        intent.putExtra("userEmail_signup", userEmail)
        intent.putExtra("userPassword_signup", userPassword)
        intent.putExtra("userUserName_signup", userUserName)
        intent.putExtra("userPhone_signup", userPhone)
        startActivity(intent)
    }

//    private fun performRegister(){
//        val userName = etName_signup.text.toString()
//        val userEmail = etEmail_signup.text.toString()
//        val userPassword = etPassword_signup.text.toString()
//        val userUserName = etUserName_signup.text.toString()
//        val userPhone = etPhone_signup.text.toString()
//
//        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userUserName.isEmpty() || userPhone.isEmpty()){
//            Toast.makeText(this, "Don't leave any fields blank!", Toast.LENGTH_SHORT).show()
//            return
//        }
//
//        auth.createUserWithEmailAndPassword(userEmail, userPassword)
//            .addOnSuccessListener {
//                Log.d("Registration", "Registration successful for uid: ${it.user.toString()}")
//                Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
//            }
//            .addOnFailureListener {
//                Log.d("Registration", "Registration failed! : ${it.message}")
//                Toast.makeText(this, "Registration Failed: ${it.message}", Toast.LENGTH_LONG).show()
//            }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
////            Toast.makeText(this, "Photo was selected", Toast.LENGTH_SHORT).show()
//            selectedPhotoUrl = data.data
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUrl)
//
//            circularImageView.setImageBitmap(bitmap)
//            btnDP.alpha = 0f
//
////            val bitmapDrawable = BitmapDrawable(bitmap)
////            btnDP.setBackgroundDrawable(bitmapDrawable)
//        }
//    }

    private fun photoPicker(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }
}