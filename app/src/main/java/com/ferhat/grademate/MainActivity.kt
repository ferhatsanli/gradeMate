package com.ferhat.grademate

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ferhat.grademate.databinding.ActivityMainBinding
import com.ferhat.grademate.databinding.NewCourseBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainB : ActivityMainBinding
    private val TAG = "FERHAT"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // address book
        mainB = ActivityMainBinding.inflate(layoutInflater)

        setContentView(mainB.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Calculate button not necessary in the beginning
        // When the app resumes onCreate triggers as well
        if (mainB.gradesList.childCount == 0)
            mainB.btnCalculate.visibility = View.INVISIBLE

        mainB.btnAddCourse.setOnClickListener {

            // init the new layout object
            val newCourseB = NewCourseBinding.inflate(layoutInflater)
            Log.i(TAG, "onCreate: new course init")

            // get selected values
            val courseName = mainB.etCourseName.text.toString()
            val score = mainB.spnCourseGrade.selectedItemPosition
            val credit = mainB.spnCourseCredit.selectedItemPosition
            Log.i(TAG, "onCreate: values ($courseName, $score, $credit)")

            // clear input field
            mainB.etCourseName.setText("")
            mainB.spnCourseGrade.setSelection(0)
            mainB.spnCourseCredit.setSelection(0)

            // set values
            newCourseB.etNewCourseName.setText(courseName)
            newCourseB.spnNewCourseGrade.setSelection(score)
            newCourseB.spnNewCourseCredit.setSelection(credit)
            Log.i(TAG, "onCreate: values uploaded")
            // delete button
            newCourseB.btnDeleteCourse.setOnClickListener {
                mainB.gradesList.removeView(newCourseB.root)
                // check list is empty, btnCalculate needed?
                if (mainB.gradesList.childCount == 0)
                    mainB.btnCalculate.visibility = View.INVISIBLE
            }

            // it's ready, add it
            layoutInflater.inflate(R.layout.new_course, null)
            mainB.gradesList.addView(newCourseB.root)
            // at least one item added... it can be visible now
            mainB.btnCalculate.visibility = View.VISIBLE
        }


    }
}