@file:Suppress("SameParameterValue")

package com.brightsolutions_knottracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class Login : AppCompatActivity() {

    // variables
    private lateinit var edUsername: EditText
    private lateinit var edPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnBackToMenu: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    private var email: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        //init db
        databaseReference = FirebaseDatabase.getInstance().reference

        // typecasting
        edUsername = findViewById(R.id.editTextTextUserNameLogin)
        edPassword = findViewById(R.id.editTextTextPasswordLogin)
        btnLogin = findViewById(R.id.buttonLogin)
        btnBackToMenu = findViewById(R.id.buttonBackToMenu)
        mAuth = FirebaseAuth.getInstance()

        // login button on click listener
        btnLogin.setOnClickListener {
            loginUser() // call login method on click
        }
    }

    private fun loginUser() {
        // try catch for error handling
        try {
            // temp variables to hold text from username and password inputs
            val username = edUsername.text.toString().trim()
            val password = edPassword.text.toString().trim()

            // Input validation --> no empty field
            if (TextUtils.isEmpty(username)) {
                Toast.makeText(this, "Please enter your username", Toast.LENGTH_SHORT).show()
                edUsername.requestFocus() // set focus to the edit text to show which element is throwing the error
                return
            }

            // Input validation --> no empty field
            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show()
                edPassword.requestFocus() // set focus to the edit text to show which element is throwing the error
                return
            }

            // make DB call to retrieve email address associated with entered username
            val userReference = databaseReference.child("userdata").child("productionUsers").child(username).child("emailAddress")
            // query for the specified path --> database ref.userdata.productionUsers."users username".emailAddress --> the query is user specific
            userReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    // Retrieve email address from the user's data
                    email = snapshot.getValue(String::class.java).toString()
                    // After retrieving the email address, attempt to sign in with email address and password
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            showToast("Login Successful!") // unable to show toasts on async method callback --> must handle toasts and intent on the main UI thread
                            clearEditTexts() // clear edit texts
                            User(username).userName = username // Set static username for the current logged-in user --> will be used throughout activity to determine current logged in user
                            startMainActivity() // navigate to home/main once logged in
                        } else {
                            showToast("Login Unsuccessful! Please try again.") // if the login fails
                            clearEditTexts() // clear edit texts
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    showToast("Data retrieval canceled") // if the query is canceled
                }
            })

        } catch (eer: Exception) {
            // catches and displays errors that occur when trying to login
            Toast.makeText(this, "Error Occurred: " + eer.message, Toast.LENGTH_SHORT).show()
        }
    }// ends login

    // method to display toast notifications when working off the main UI thread
    private fun showToast(message: String) {
        Handler(mainLooper).post {
            Toast.makeText(this@Login, message, Toast.LENGTH_SHORT).show()
        }
    }

    // clear text boxes method --> called after login fail or login success
    private fun clearEditTexts() {
        Handler(mainLooper).post {
            edUsername.setText("")
            edPassword.setText("")
            edUsername.requestFocus()
        }

    }

    // method that is used to start an intent to the main activity from the async method callback
    private fun startMainActivity() {
        Handler(mainLooper).post {
            val intent = Intent(this@Login, MainActivity::class.java) // intent object --> home screen
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // flag clears the activity stack and makes the new activity the only one on the stack
            startActivity(intent) // begin activity
            finish() // prevents keying back to login
        }
    }

    fun backToMenu(view: View) {
        // on click for back to menu button
        if (view.id == R.id.buttonBackToMenu){
            val intent = Intent(this, StartupSplash::class.java)
            startActivity(intent)
        }
    }
}