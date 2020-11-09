package com.nimitsajal.studentconnect

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_colege_details.*
import kotlinx.android.synthetic.main.activity_sign_up.toLoginPage

class colegeDetails : AppCompatActivity() {

    var university_name: String? = null
    var college_name: String? = null
    var branch_name: String? = null
    var semester_name: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_colege_details)

        val db = FirebaseFirestore.getInstance()

        toLoginPage.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        btnRegister.setOnClickListener {
            performRegister()

            displaySavedData()
        }

        val University = mutableListOf<String>("University")
        University.add("BMS University")
        University.add("PES University")
        University.add("RV University")

        var College = mutableListOf<String>("College")
//        College.add("College")
//        var college_display = mutableListOf<String>()
//        var i = 0
//        for(element in College){
//            if(i == 0){
//                continue
//            }
//            else{
//                college_display.add(element)
//                i++
//            }
//        }

        var Branch = mutableListOf<String>("Branch")
//        Branch.add("Branch")
//        var branch_display = mutableListOf<String>()
//        i = 0
//        for(element in College){
//            if(i == 0){
//                continue
//            }
//            else{
//                branch_display.add(element)
//                i++
//            }
//        }


        var Semester = mutableListOf<String>("Semester")
//        Semester.add("Semester")
//        var semester_display = mutableListOf<String>()
//        i = 0
//        for(element in College){
//            if(i == 0){
//                continue
//            }
//            else{
//                semester_display.add(element)
//                i++
//            }
//        }

        var university_position = 0
        var college_position = 0
        var branch_position = 0
        var semester_position = 0

        val spinnerUniversity = ddUniversity
        if(spinnerUniversity != null){
            val adapter = ArrayAdapter(this, R.layout.style_spinner, University)
            spinnerUniversity.adapter = adapter

            spinnerUniversity.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    university_name = University[position]
                    if(position != university_position){
//                        College.removeAt(0)
                        College.clear()
                        College.add("College")
                        university_position = position
                        college_position = 0
                        branch_position = 0
                        semester_position = 0
                        College.add("BMSCE - BMS College of Engineering")
                        College.add("BMSCA - BMS College of Architecture")
                        College.add("BMSCL - BMS College of Law")
                        Branch.clear()
                        Branch.add("Branch")
                        college_name = College[0]
                        branch_name = Branch[0]
                        Semester.clear()
                        Semester.add("Semester")
                        semester_name = Semester[0]
//                        University.removeAt(0)
                    }
//                    else{
//                        College.clear()
//                        College.add("College")
//                        college_name = College[0]
//                        Branch.clear()
//                        Branch.add("Branch")
//                        branch_name = Branch[0]
//                        Semester.clear()
//                        Semester.add("Semester")
//                        semester_name = Semester[0]
//                    }

                    }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    university_name = University[0]
                }
            }
        }

        val spinnerCollege = ddCollege
        if(spinnerCollege != null){
            val adapter = ArrayAdapter(this, R.layout.style_spinner, College)
            spinnerCollege.adapter = adapter

            spinnerCollege.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    college_name = College[position]
                    if(position != college_position){
//                        Branch.removeAt(0)
                        Branch.clear()
                        Branch.add("Branch")
                        college_position = position
                        branch_position = 0
                        semester_position = 0
                        Branch.add("M.Tech - Machine Design")
                        Branch.add("M.Tech - Computer Network Engineering")
                        Branch.add("BE - Computer Science & Engineering")
                        branch_name = Branch[0]
                        Semester.clear()
                        Semester.add("Semester")
                        semester_name = Semester[0]
                    }
//                    else{
//                        Branch.clear()
//                        Branch.add("Branch")
//                        branch_name = Branch[0]
//                        Semester.clear()
//                        Semester.add("Semester")
//                        semester_name = Semester[0]
//                    }

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    college_name = College[0]
                }
            }
        }

        val spinnerBranch = ddBranch
        if(spinnerBranch != null){
            val adapter = ArrayAdapter(this, R.layout.style_spinner, Branch)
            spinnerBranch.adapter = adapter

            spinnerBranch.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    branch_name = Branch[position]
                    if(position != branch_position){
//                        Semester.removeAt(0)
                        Semester.clear()
                        branch_position = position
                        semester_position = 0
                        Semester.add("Semester")
                        Semester.add("Semester 1")
                        Semester.add("Semester 2")
                        Semester.add("Semester 3")
                        semester_name = Semester[0]
                    }
//                    else{
//                        Semester.clear()
//                        Semester.add("Semester")
//                        semester_name = Semester[0]
//                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    branch_name = Branch[0]
                }
            }
        }

        val spinnerSemester = ddSemester
        if(spinnerSemester != null){
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

    private fun displaySavedData(){
        Log.d("display", university_name.toString())
        Log.d("display", college_name.toString())
        Log.d("display", branch_name.toString())
        Log.d("display", semester_name.toString())

//        Toast.makeText(this, university_name, Toast.LENGTH_LONG).show()
//        Toast.makeText(this, college_name, Toast.LENGTH_LONG).show()
//        Toast.makeText(this, branch_name, Toast.LENGTH_LONG).show()
//        Toast.makeText(this, semester_name, Toast.LENGTH_LONG).show()
    }

    private fun uploadImageToFirebaseStorage(selectedPhotoUrl: Uri?, userName: String){
        if(selectedPhotoUrl == null){
            Log.d("dp", "nothing")
            return
//            return "https://firebasestorage.googleapis.com/v0/b/student-connect-b96e6.appspot.com/o/user_dp%2Fuser_default_dp.png?alt=media&token=4a2736ef-c5cb-4845-9d0f-894e7bf3c6a2"
        }

//        val filename = UUID.randomUUID().toString()
        val ref = FirebaseStorage.getInstance().getReference("user_dp/$userName")
        ref.putFile(selectedPhotoUrl)
            .addOnSuccessListener {
                Log.d(
                    "Registration",
                    "Image successfully uploaded at location: ${it.metadata?.path}"
                )
                ref.downloadUrl
                    .addOnSuccessListener { result ->
                        Log.d("dp", "image url: ${result.toString()}")
//                        return result.toString()
                    }
            }
            .addOnFailureListener {
                Log.d("dp", "Image upload failed: ${it.message}")
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