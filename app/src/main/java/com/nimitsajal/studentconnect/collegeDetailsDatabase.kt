package com.nimitsajal.studentconnect

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

import kotlinx.android.synthetic.main.activity_colege_details.*
import kotlinx.android.synthetic.main.activity_sign_up.toLoginPage

class collegeDetailsDatabase : AppCompatActivity() {
    var university_name: String = "University"
    var college_name: String = "College"
    var branch_name: String = "Branch"
    var semester_name: String = "Semester"

    private lateinit var storageReference: StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colege_details)

        val db = FirebaseFirestore.getInstance()

        storageReference = FirebaseStorage.getInstance().reference

        toLoginPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            performRegister()
            displaySavedData()
        }

        val University = setArrayUniversity()

        val College = mutableListOf<String>("College")
//        val College = setArrayCollege(university_name!!, College_temp)

        val Branch = mutableListOf<String>("Branch")

        val Semester = mutableListOf<String>("Year")


        var university_position = 0
        var college_position = 0
        var branch_position = 0
        var semester_position = 0

        val spinnerUniversity = ddUniversity
        if (spinnerUniversity != null) {
            val adapter = ArrayAdapter(this, R.layout.style_spinner, University)
            spinnerUniversity.adapter = adapter

            spinnerUniversity.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    university_name = University[position]
                    if (position != university_position) {
                        Log.d("database", "university_name = $university_name, university_position = $position")
//                        College.clear()
                        university_position = position
                        college_position = 0
                        branch_position = 0
                        semester_position = 0
                        setArrayCollege(university_name, College)
                        Branch.clear()
                        Branch.add("Branch")
                        college_name = College[0]
                        branch_name = Branch[0]
                        Semester.clear()
                        Semester.add("Semester")
                        semester_name = Semester[0]
                    }
                }
                override fun onNothingSelected(p0: AdapterView<*>?) {
                    university_name = University[0]
                }
            }
        }

        val spinnerCollege = ddCollege
        if (spinnerCollege != null) {
            val adapter = ArrayAdapter(this, R.layout.style_spinner, College)
            spinnerCollege.adapter = adapter

            spinnerCollege.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    college_name = College[position]
                    if (position != college_position) {

//                        Branch.clear()
//                        Branch.add("Branch")

                        setArrayBranch(university_name, college_name, Branch)

                        college_position = position
                        branch_position = 0
                        semester_position = 0
                        branch_name = Branch[0]
                        Semester.clear()
                        Semester.add("Semester")
                        semester_name = Semester[0]
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    college_name = College[0]
                }
            }
        }

        val spinnerBranch = ddBranch
        if (spinnerBranch != null) {
            val adapter = ArrayAdapter(this, R.layout.style_spinner, Branch)
            spinnerBranch.adapter = adapter

            spinnerBranch.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    branch_name = Branch[position]
                    if (position != branch_position) {

//                        Semester.clear()
                        setArraySemester(university_name, college_name, branch_name, Semester)
                        branch_position = position
                        semester_position = 0
                        semester_name = Semester[0]
                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    branch_name = Branch[0]
                }
            }
        }

        val spinnerSemester = ddSemester
        if (spinnerSemester != null) {
            val adapter = ArrayAdapter(this, R.layout.style_spinner, Semester)
            spinnerSemester.adapter = adapter

            spinnerSemester.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    semester_name = Semester[position]

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    semester_name = Semester[0]
                }
            }
        }
    }

    private fun setArrayUniversity(): MutableList<String> {
        val db = FirebaseFirestore.getInstance()

        val University = mutableListOf<String>("University")

        db.collection("University")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("database", document.id)
                    if(document.id == "Next"){
                        continue
                    }
                    else{
                        University.add(document.id)
                    }
                }
            }
            .addOnFailureListener { exception ->
                Log.d("database", "Error getting documents: ", exception)
            }
        return University
    }

    private fun setArrayCollege(university_name: String, College: MutableList<String>){
        val db = FirebaseFirestore.getInstance()

        College.clear()
        College.add("College")

        db.collection("University").document("Next").collection(university_name)
            .get()
            .addOnSuccessListener {result ->
                for (document in result) {
                    Log.d("database", document.id)
                    if(document.id != "Next"){
                        College.add(document.id)
                    }
//                    College.add(document.id)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("database", "Error getting documents: ", exception)
            }
    }

    private fun setArrayBranch(university_name: String, college_name: String, Branch: MutableList<String>){
        val db = FirebaseFirestore.getInstance()

        Branch.clear()
        Branch.add("Branch")

        db.collection("University").document("Next").collection(university_name).document("Next").collection(college_name)
            .get()
            .addOnSuccessListener {result ->
                for (document in result) {
                    Log.d("database", document.id)
                    if(document.id != "Next"){
                        Branch.add(document.id)
                    }
//                    Branch.add(document.id)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("database", "Error getting documents: ", exception)
            }
    }

    private fun setArraySemester(university_name: String, college_name: String, branch_name: String, Semester: MutableList<String>){
        val db = FirebaseFirestore.getInstance()

        Semester.clear()
        Semester.add("Year")

        db.collection("University").document("Next").collection(university_name).document("Next").collection(college_name).document("Next").collection(branch_name)
            .get()
            .addOnSuccessListener {result ->
                for (document in result) {
                    Log.d("database", document.id)
                    if(document.id != "0"){
                        Semester.add(document.id)
                    }
//                    Branch.add(document.id)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("database", "Error getting documents: ", exception)
            }
    }

    private fun displaySavedData() {
        Log.d("display", university_name.toString())
        Log.d("display", college_name.toString())
        Log.d("display", branch_name.toString())
        Log.d("display", semester_name.toString())

    }

//    private fun uploadImageToFirebaseStorage(selectedPhotoUrl: Uri?, userName: String){
//        if(selectedPhotoUrl == null){
//            Log.d("dp", "nothing")
//            return
////            return "https://firebasestorage.googleapis.com/v0/b/student-connect-b96e6.appspot.com/o/user_dp%2Fuser_default_dp.png?alt=media&token=4a2736ef-c5cb-4845-9d0f-894e7bf3c6a2"
//        }
//
//        val filename = UUID.randomUUID().toString()
//        val ref = FirebaseStorage.getInstance().getReference("/user_dp/$userName")
//        ref.putFile(selectedPhotoUrl)
//            .addOnSuccessListener {
//                Log.d(
//                    "Registration",
//                    "Image successfully uploaded at location: ${it.metadata?.path}"
//                )
//                ref.downloadUrl
//                    .addOnSuccessListener { result ->
//                        Log.d("dp", "image url: ${result.toString()}")
////                        return result.toString()
//                    }
//            }
//            .addOnFailureListener {
//                Log.d("dp", "Image upload failed: ${it.message}")
//            }
//    }


    private fun uploadImageToFirebaseStorage(selectedPhotoUrl: Uri?, userName: String) {
        if(selectedPhotoUrl == null){
            return
        }
        else{
            val photoReference = storageReference.child("gs://student-connect-b96e6.appspot.com/user_dp/$userName-photo.jpg")
            photoReference.putFile(selectedPhotoUrl)
                .addOnSuccessListener {
                    Log.d("dp", it.totalByteCount.toString())
                }
                .addOnFailureListener {
                    Log.d("dp", it.message.toString())
                }
        }
    }


    private fun performRegister(){
//        val userName = etName_signup.text.toString()
//        val userEmail = etEmail_signup.text.toString()
//        val userPassword = etPassword_signup.text.toString()
//        val userUserName = etUserName_signup.text.toString()
//        val userPhone = etPhone_signup.text.toString()

        Toast.makeText(this, "Entered Perform Register", Toast.LENGTH_SHORT).show()

        val userName = intent.getStringExtra("userName_signup")
        val userEmail = intent.getStringExtra("userEmail_signup")
        val userPassword = intent.getStringExtra("userPassword_signup")
        val userUserName = intent.getStringExtra("userUserName_signup")
        val userPhone = intent.getStringExtra("userPhone_signup")
        val selectedPhotoUrl_string: String? = intent.getStringExtra("dpImage_string")

        val selectedPhotoUrl = Uri.parse(selectedPhotoUrl_string)


        if (userEmail != null) {
            if (userPassword != null) {
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(userEmail, userPassword)
                    .addOnSuccessListener {
                        Log.d(
                            "Registration",
                            "Registration successful for uid: ${it.user.toString()}"
                        )
                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                        if (userName != null) {
                            uploadImageToFirebaseStorage(selectedPhotoUrl, userName)
                        }
                    }
                    .addOnFailureListener {
                        Log.d("Registration", "Registration failed! : ${it.message}")
                        Toast.makeText(
                            this,
                            "Registration Failed: ${it.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
            }
        }
    }
}