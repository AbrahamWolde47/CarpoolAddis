package com.abraham.carpooladdis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mDriver = findViewById<Button>(R.id.driver) as Button
        val mPassenger = findViewById<Button>(R.id.passenger) as Button

        mDriver.setOnClickListener {
            val intent = Intent(this, DriverLoginActivity::class.java)
            startActivity(intent)
        }

        mPassenger.setOnClickListener{
            val intent = Intent(this, PassengerLoginActivity::class.java)
            startActivity(intent)
        }
    }


}
