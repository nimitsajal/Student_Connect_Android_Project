package com.nimitsajal.studentconnect

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.util.*
import java.util.regex.Pattern

class Sign_up : AppCompatActivity() {

    private var selectedPhotoUrl: Uri? = null
    private lateinit var auth: FirebaseAuth

    private var isUserPresent = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        auth = FirebaseAuth.getInstance()

        val userName = intent.getStringExtra("userName_signup")
        val userEmail = intent.getStringExtra("userEmail_signup")
        val userPassword = intent.getStringExtra("userPassword_signup")
        val userUserName = intent.getStringExtra("userUserName_signup")
        val userPhone = intent.getStringExtra("userPhone_signup")
        val selectedPhotoUrl_string: String? = intent.getStringExtra("dpImage_string")
        val selectedPhoto = Uri.parse(selectedPhotoUrl_string)

        if(userName != null){
            etName_signup.setText(userName.toString())
        }
        if(userEmail != null){
            etEmail_signup.setText(userEmail.toString())
        }
        if(userPassword != null){
            etPassword_signup.setText(userPassword.toString())
        }
        if(userPhone != null){
            etPhone_signup.setText(userPhone.toString())
        }
//        if(selectedPhoto != null){
//            selectedPhotoUrl = selectedPhoto
//            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUrl)
//            circularImageView.setImageBitmap(bitmap)
//            btnDP.alpha = 0f
//        }


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

    private fun isUserNameValid(userName: String): Boolean {
        return true     //BACKEND INTEGRATION PENDING
    }

    private fun isEmailValid(email: CharSequence): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    private fun isValidMobile(phone: String): Boolean {
//        if(Pattern.matches("(6-9) + [0-9]+", phone) && phone.length == 10 ) {
//            return true
//        }
        if((Pattern.matches("6[0-9]+", phone) or Pattern.matches("7[0-9]+", phone) or Pattern.matches("8[0-9]+", phone) or Pattern.matches("9[0-9]+", phone)) && phone.length == 10){
            return true
        }
        return false
    }


    private fun isPasswordValid(password: String): Boolean {
        if(Pattern.matches("^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$", password) && password.length >= 6){
            return true
        }
        return false
    }

//    @Synchronized fun retFalse(){
//        isUserPresent = false
//    }
//
//    @Synchronized fun retTrue(){
//        isUserPresent = true
//    }

//    @Synchronized private fun checkUserExist(userName: String):Boolean {
//        val db = FirebaseFirestore.getInstance()
//        retTrue()
//        db.collection("User")
//            .get()
//            .addOnSuccessListener { result ->
//                for(document in result){
//                    if(document.id == userName){
//                        Log.d("ret", " in if, isUserPresent = $isUserPresent, username in db = ${document.id}, username entered = $userName")
//                        retFalse()
//                        Log.d("ret", " in if, isUserPresent = $isUserPresent")
//                    }
//                }
//
//                Log.d("ret", " in success, isUserPresent = $isUserPresent")
//                //retFalse()
//                Log.d("ret", " in success, isUserPresent = $isUserPresent")
//            }
//            .addOnFailureListener { exception ->
//                Log.d("ret", " in failure, isUserPresent = $isUserPresent")
//                retTrue()
//                Log.d("ret", " in failure, isUserPresent = $isUserPresent")
//            }
//        return isUserPresent
//    }

    @Synchronized fun goToCollegeDetails(){


        val userName = etName_signup.text.toString()
        val userEmail = etEmail_signup.text.toString()
        val userPassword = etPassword_signup.text.toString()
        val userUserName = etUserName_signup.text.toString()
        val userPhone = etPhone_signup.text.toString()

        if(userName.isEmpty() || userEmail.isEmpty() || userPassword.isEmpty() || userUserName.isEmpty() || userPhone.isEmpty()){
            Toast.makeText(this, "Don't leave any fields blank!", Toast.LENGTH_SHORT).show()
            return
        }

//        isUserPresent = checkUserExist(userUserName)

        val db = FirebaseFirestore.getInstance()
//        retTrue()
        db.collection("User")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    if(document.id == userUserName){
//                        Log.d("ret", " in if, isUserPresent = $isUserPresent, username in db = ${document.id}, username entered = $userUserName")
//                        retFalse()
//                        Log.d("ret", " in if, isUserPresent = $isUserPresent")
                        Toast.makeText(this, "Username already exists!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Sign_up::class.java)
                        intent.putExtra("userName_signup", userName)
                        intent.putExtra("userEmail_signup", userEmail)
                        intent.putExtra("userPassword_signup", userPassword)
                        intent.putExtra("userUserName_signup", userUserName)
                        intent.putExtra("userPhone_signup", userPhone)
                        intent.putExtra("dpImage_string", selectedPhotoUrl.toString())
                        startActivity(intent)
                        finish()
                    }
                }


//                Log.d("ret", " in success, isUserPresent = $isUserPresent")
//                //retFalse()
//                Log.d("ret", " in success, isUserPresent = $isUserPresent")
            }
            .addOnFailureListener { exception ->
//                Log.d("ret", " in failure, isUserPresent = $isUserPresent")
//                retTrue()
//                Log.d("ret", " in failure, isUserPresent = $isUserPresent")
            }

//        if(!isUserPresent){
//            Toast.makeText(this, "User Already Exists", Toast.LENGTH_SHORT).show()
//            return
//        }

        if(!isEmailValid(userEmail)){
            Toast.makeText(this, "Email is Invalid!", Toast.LENGTH_SHORT).show()
            return
        }

        if(!isValidMobile(userPhone)){
            Toast.makeText(this, "Phone number is Invalid!", Toast.LENGTH_SHORT).show()
            return
        }

        if(!isPasswordValid(userPassword)){
            Toast.makeText(this, "Weak Password!", Toast.LENGTH_SHORT).show()
            return
        }

        uploadImageToFirebaseStorage()

        val intent = Intent(this, collegeDetailsDatabase::class.java)
        intent.putExtra("userName_signup", userName)
        intent.putExtra("userEmail_signup", userEmail)
        intent.putExtra("userPassword_signup", userPassword)
        intent.putExtra("userUserName_signup", userUserName)
        intent.putExtra("userPhone_signup", userPhone)
        intent.putExtra("dpImage_string", selectedPhotoUrl.toString())
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0 && resultCode == Activity.RESULT_OK && data != null){
//            Toast.makeText(this, "Photo was selected", Toast.LENGTH_SHORT).show()
            selectedPhotoUrl = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedPhotoUrl)
            circularImageView.setImageBitmap(bitmap)
            btnDP.alpha = 0f

//            val bitmapDrawable = BitmapDrawable(bitmap)
//            btnDP.setBackgroundDrawable(bitmapDrawable)
        }
    }

    private fun photoPicker(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }




    private fun uploadImageToFirebaseStorage(){
        if(selectedPhotoUrl == null) {
            return
        }

        val filename = UUID.randomUUID().toString()
//        val filename = "morty.jpg"
        val ref = FirebaseStorage.getInstance().getReference("/images/dp/$filename")
        ref.putFile(selectedPhotoUrl!!)
            .addOnSuccessListener {
                Log.d("Registration", "Image successfully uploaded at location: ${it.metadata?.path}")
                ref.downloadUrl
                    .addOnSuccessListener {
                        Log.d("Registration","image url: $it")
//                        saveUserToFirebaseDatabase(it.toString())
                    }
            }
            .addOnFailureListener {
                Log.d("Registration", "Image upload failed: ${it.message}")
            }
    }
}
