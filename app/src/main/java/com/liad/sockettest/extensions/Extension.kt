package com.liad.sockettest.extensions

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.liad.sockettest.R

fun changeFragment(
    activity: FragmentActivity,
    fragment: Fragment,
    bundle: Bundle? = null,
    addToStack: Boolean = false
) {
    val fragmentTransaction = activity.supportFragmentManager.beginTransaction()
    if (addToStack) fragmentTransaction.addToBackStack(null)
    fragment.arguments = bundle
    fragmentTransaction.replace(
        R.id.main_activity_frame_layout,
        fragment/*,
        fragment::class.simpleName*/
    ).commit()
}

fun log(output: String, key: String = "Liad") {
    Log.d(key, output)
}

fun Context.toast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (text.isNotEmpty()) Toast.makeText(this, text, duration).show()
}
