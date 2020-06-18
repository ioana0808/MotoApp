package com.android.example.motoapp

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LogInActivity: AppCompatActivity(){


    private var btnCreateAcc: Button?=null
    private var btnLogIn: Button?=null
    private var usernameSignUp: EditText?=null
    private var passwordSignUp: EditText?=null
    private  var auth: FirebaseAuth?=null


    //OnCreate function--Main Constructor
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val actionbar=supportActionBar
        actionbar!!.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayShowHomeEnabled(true)

        btnLogIn = findViewById(R.id.loginButton)
        btnCreateAcc = findViewById(R.id.createAccountBtn)

        usernameSignUp= findViewById(R.id.username)
        passwordSignUp= findViewById(R.id.password)

        auth = FirebaseAuth.getInstance()


        btnLogIn?.setOnClickListener {
            val email = usernameSignUp?.text.toString()
            val password = passwordSignUp?.text.toString()


            if (TextUtils.isEmpty(email)) {
                usernameSignUp?.error = "Please enter email"
                return@setOnClickListener
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            {
                usernameSignUp?.error="Please enter email"
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                passwordSignUp?.error = "Please enter password"
                return@setOnClickListener
            }
            doLogIn(email, password)
        }


        btnCreateAcc?.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    //LogIn Function
    private fun doLogIn(email: String, password: String){
        auth?.signInWithEmailAndPassword(email, password)
            ?.addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = Intent(applicationContext , DashboardActivity::class.java)
                    startActivity(user)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication failed.${task.exception}", Toast.LENGTH_SHORT).show()
                }
            }
    }

}
