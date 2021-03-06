package com.liad.sockettest.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.liad.sockettest.R
import com.liad.sockettest.utills.extensions.changeFragment
import com.liad.sockettest.fragments.MainFragment
import com.liad.sockettest.managers.SocketWebManager

class MainActivity : AppCompatActivity() {

    private val mSocket = SocketWebManager.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        changeFragment(this, MainFragment.newInstance())
    }
}
