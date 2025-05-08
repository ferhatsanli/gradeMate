package com.ferhat.grademate

import android.content.Context
import android.util.Log
import com.shashank.sony.fancytoastlib.FancyToast

fun Any.logi(message: String){
    val tag = "FERHAT"
    Log.i(this::class.java.simpleName, message)
}

fun Context.popmsg(message: String) {
    val msgfinal = this::class.java.simpleName + " >> " + message
    FancyToast.makeText(this, msgfinal, FancyToast.LENGTH_LONG, FancyToast.INFO, true).show()

}