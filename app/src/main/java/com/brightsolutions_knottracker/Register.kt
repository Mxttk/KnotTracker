package com.brightsolutions_knottracker

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class Register : AppCompatActivity() {

    // variables
    private lateinit var userUserName: EditText
    private lateinit var userFirstName: EditText
    private lateinit var userLastName: EditText
    private lateinit var edUserName: EditText
    private lateinit var edPassword: EditText
    private lateinit var edConfirmPassword: EditText
    private lateinit var btnRegister: Button
    private lateinit var btnBackToMenu: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference

        // typecasting
        userUserName = findViewById(R.id.editTextTextUserName)
        userFirstName = findViewById(R.id.editTextTextFirstNameReg)
        userLastName = findViewById(R.id.editTextTextLastNameReg)
        edUserName = findViewById(R.id.editTextTextEmailAddress)
        edPassword = findViewById(R.id.editTextTextPassword)
        edConfirmPassword = findViewById(R.id.editTextTextPasswordConfirm)
        btnRegister = findViewById(R.id.buttonReg)
        btnBackToMenu = findViewById(R.id.buttonBackToMenu)

        // firebase instance
        mAuth = FirebaseAuth.getInstance()
    }

    fun registerUser(view: View) {
        // on click for register button
        if (view.id == R.id.buttonReg)
        {
            // grabbing the text values entered by the user
            val username = userUserName.text.toString().trim()
            val firstName = userFirstName.text.toString().trim()
            val lastName = userLastName.text.toString().trim()
            val email = edUserName.text.toString().trim()
            val passwordReg = edPassword.text.toString().trim()
            val conPassword = edConfirmPassword.text.toString().trim()

            // input validation --> make sure no empty submissions
            if (TextUtils.isEmpty(username))
            {
                Toast.makeText(this, "Please enter an appropriate username :)", Toast.LENGTH_SHORT).show()
                return
            }

            // input validation --> make sure no empty submissions
            if (TextUtils.isEmpty(firstName))
            {
                Toast.makeText(this, "Please enter your first name :)", Toast.LENGTH_SHORT).show()
                return
            }

            // input validation --> make sure no empty submissions
            if (TextUtils.isEmpty(lastName))
            {
                Toast.makeText(this, "Please enter your last name :)", Toast.LENGTH_SHORT).show()
                return
            }

            // input validation --> make sure no empty submissions
            if (TextUtils.isEmpty(email))
            {
                Toast.makeText(this, "User email address can't be empty", Toast.LENGTH_SHORT).show()
                return
            }

            // input validation --> make sure no empty submissions
            if (TextUtils.isEmpty(passwordReg))
            {
                Toast.makeText(this, "User password can't be empty", Toast.LENGTH_SHORT).show()
                return
            }

            // input validation --> make sure no empty submissions
            if (TextUtils.isEmpty(conPassword))
            {
                Toast.makeText(this, "Confirmed password can't be empty", Toast.LENGTH_SHORT).show()
                return
            }

            // if the user entered passwords match --> attempt to register the user
            if (conPassword == passwordReg) {
                mAuth.createUserWithEmailAndPassword(email, passwordReg) // use FB instance to attempt to create account with the users email address and password
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(
                                this@Register,
                                "Registration successful! :) ",
                                Toast.LENGTH_SHORT
                            ).show()
                            // create associated user data
                            val newUser = CreateUserData() // object of create user data class
                            newUser.createUserDataOnReg(username,firstName,lastName,email) // call create user data method and pass in username, first name, last name and email as arguments
                            val intent = Intent(this@Register, StartupSplash::class.java) // intent object --> back to splash screen
                            startActivity(intent) // begin intent
                            finish() // end current activity
                        } else {
                            // if the email is already in use
                            Toast.makeText(
                                this@Register,
                                "Registration failed :( \nThis email address is already in use!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }
            else {
                // if the passwords do not match --> execute this section
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show() // display error message
                return
            }
        }
    }

    fun backToMenu(view: View) {
        // on click for back to menu button
        if (view.id == R.id.buttonBackToMenu){
            val intent = Intent(this, StartupSplash::class.java) // create intent object for back to startup splash
            startActivity(intent) // begin intent object
        }
    }

}