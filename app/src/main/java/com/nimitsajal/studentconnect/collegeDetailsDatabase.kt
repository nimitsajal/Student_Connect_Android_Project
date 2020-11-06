package com.nimitsajal.studentconnect

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.*
import kotlinx.android.synthetic.main.activity_colege_details.*
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.activity_sign_up.toLoginPage

class collegeDetailsDatabase : AppCompatActivity() {
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


            displaySavedData()
        }

        val University = setArrayUniversity()
        var College = mutableListOf<String>("College")


        var Branch = mutableListOf<String>("Branch")



        var Semester = mutableListOf<String>("Semester")


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

                        Semester.clear()
                        branch_position = position
                        semester_position = 0
                        Semester.add("Semester")
                        Semester.add("Semester 1")
                        Semester.add("Semester 2")
                        Semester.add("Semester 3")
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

        db.collection("College Groups")
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("database", "${document.id} => ${document.data}")
                    University.add(document.id)
                }
            }
            .addOnFailureListener { exception ->
                Log.d("database", "Error getting documents: ", exception)
            }
        return University
    }

//    private fun setArrayCollege(university_name: String): MutableList<String> {
//        val db = FirebaseFirestore.getInstance()
//
//        val University = mutableListOf<String>("University")
//
//        db.collection("College Groups").document(university_name)
//            .get()
//            .addOnSuccessListener { result ->
//                for (collection in result) {
//                    Log.d("database", "${document.id} => ${document.data}")
//                    University.add(document.id)
//                }
//            }
//            .addOnFailureListener { exception ->
//                Log.d("database", "Error getting documents: ", exception)
//            }
//        return University
//    }

    private fun displaySavedData() {
        Log.d("display", university_name.toString())
        Log.d("display", college_name.toString())
        Log.d("display", branch_name.toString())
        Log.d("display", semester_name.toString())

    }
}