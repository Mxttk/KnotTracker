package com.brightsolutions_knottracker

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

@Suppress("UNUSED_PARAMETER")
class StartupSplash : AppCompatActivity() {
    private lateinit var buttonSignIn: Button
    private lateinit var butttonReg: Button
    private lateinit var buttonKnotList: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_startup_splash)

        // on object init --> seed user
        SeedUser()

        buttonSignIn = findViewById(R.id.buttonSignIn)
        butttonReg = findViewById(R.id.buttonRegister)
        buttonKnotList = findViewById(R.id.buttonKnotListSplash)
    }

    fun regUser(view : View)
    {
        val intent = Intent(this@StartupSplash, Register::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // flag clears the activity stack and makes the new activity the only one on the stack
        startActivity(intent)
        finish()
    }

    fun loginUser(view : View)
    {
        val intent = Intent(this@StartupSplash, Login::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // flag clears the activity stack and makes the new activity the only one on the stack
        startActivity(intent)
        finish()
    }

    fun knotList(view : View)
    {
        val intent = Intent(this@StartupSplash, KnotList::class.java)
        startActivity(intent)
    }


}