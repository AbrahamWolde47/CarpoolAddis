package com.abraham.carpooladdis

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import com.google.firebase.auth.FirebaseAuth

class PassengerLoginActivity : AppCompatActivity() {

    private val TAG = "LoginActivity"
    //global variables

    private var email: String? = null
    private var password: String? = null

    private var etEmail: EditText? = null
    private var etPassword: EditText? = null
    private var btnLogin: Button? = null
    private var tvForgot: TextView? = null
    private var btnCreate: Button? = null

    //Firebase references
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_driver_login)

        initialise()
    }

    private fun initialise() {
        etEmail = findViewById<View>(R.id.et_email) as EditText
        etPassword = findViewById<View>(R.id.et_password) as EditText
        btnLogin = findViewById<View>(R.id.btn_login) as Button
        btnCreate = findViewById<View>(R.id.btn_register_account) as Button
        tvForgot = findViewById<View>(R.id.tv_forgot_password) as TextView

        mAuth = FirebaseAuth.getInstance()

        tvForgot!!
                .setOnClickListener { startActivity(Intent(this@PassengerLoginActivity,
                        ForgotPasswordActivity::class.java)) }

        btnCreate!!.setOnClickListener{ startActivity(Intent(this@PassengerLoginActivity, PassengerAccountCreateActivity::class.java) )}


        btnLogin!!.setOnClickListener { loginUser() }
    }



    private fun loginUser() {
        email = etEmail?.text.toString()
        password = etPassword?.text.toString()
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

            Log.d(TAG, "Logging in user.")
            mAuth!!.signInWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with signed-in user's information
                            Log.d(TAG, "signInWithEmail:success")
                            updateUI()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithEmail:failure", task.exception)
                            Toast.makeText(this@PassengerLoginActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        } else {
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        val intent = Intent(this@PassengerLoginActivity, MapsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
    }


}
