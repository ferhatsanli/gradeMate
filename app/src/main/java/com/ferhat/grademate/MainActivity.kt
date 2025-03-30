package com.ferhat.grademate

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ferhat.grademate.databinding.ActivityMainBinding
import com.ferhat.grademate.databinding.NewCourseBinding

class MainActivity : AppCompatActivity() {
    private lateinit var mainB : ActivityMainBinding
    private val TAG = "FERHAT"
    private val courses = "Math,GYM,Science,Computer,Art".split(',')
    private var scoresData: MutableList<CourseData> = mutableListOf()
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

        // autocomplete for et
        val courseAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_spinner_dropdown_item,
            courses)

        mainB.etCourseName.setAdapter(courseAdapter)
        // Calculate button not necessary in the beginning
        // When the app resumes onCreate triggers as well
        if (mainB.gradesList.childCount == 0)
            mainB.btnCalculate.visibility = View.INVISIBLE

        mainB.btnAddCourse.setOnClickListener {
            if (!mainB.etCourseName.text.isNullOrEmpty()) {
                // init the new layout object
                val newCourseB = NewCourseBinding.inflate(layoutInflater)
                Log.i(TAG, "onCreate: new course init")

                // autocomplete for et
                newCourseB.etNewCourseName.setAdapter(courseAdapter)

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
            else
                Toast.makeText(this, "Please enter a class name!", Toast.LENGTH_LONG)
                    .show()
        }


    }

    fun calculateAverage(v: View) {
        var totalScore = 0.0
        var totalCredit = 0.0
        for (i in 0..mainB.gradesList.childCount){
            val tempCourseDatas = mainB.gradesList.getChildAt(i)
            Log.i(TAG, "calculateAverage: $tempCourseDatas")
            val tempCourseBinding = NewCourseBinding.bind(tempCourseDatas)
            Log.i(TAG, "calculateAverage: ${tempCourseBinding.etNewCourseName.text}")
            scoresData.add(CourseData(tempCourseBinding.etNewCourseName.text.toString(),
                tempCourseBinding.spnNewCourseGrade.selectedItemPosition + 1,
                tempCourseBinding.spnNewCourseCredit.selectedItem.toString()))
            Log.i(TAG, "calculateAverage: ${tempCourseBinding.spnNewCourseGrade.selectedItemPosition + 1}")
            totalCredit += scoresData.last().credit + 1
            Log.i(TAG, "calculateAverage: ${scoresData.last().credit}")
            totalScore += noteToScore(scoresData.last().scoreCode) * (scoresData.last().credit)
            Log.i(TAG, "calculateAverage: $totalScore")
        }
        Toast.makeText(this, "Average: ${totalScore / totalCredit}", Toast.LENGTH_LONG)
            .show()
    }

    fun noteToScore(score: String): Double {
        return when (score) {
            "AA" -> 4.0
            "BA" -> 3.5
            "BB" -> 3.0
            "CB" -> 2.5
            "CC" -> 2.0
            "DC" -> 1.5
            "DD" -> 1.0
            "FF" -> 0.5
            else -> 0.0
        }
    }

}