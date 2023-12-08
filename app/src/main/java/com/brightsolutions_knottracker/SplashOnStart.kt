package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.*
import android.widget.ImageView

@SuppressLint("StaticFieldLeak")
private lateinit var aniSplash: ImageView
private lateinit var animation: Animation

class SplashOnStart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_on_start)

        // typecasting
        aniSplash = findViewById(R.id.imageViewSplashAnimation)
        // set animation source --> bounce_melt
        animation = AnimationUtils.loadAnimation(this, R.anim.bounce_melt)
        // set a listener for the animation
        animation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation) {
                // Do nothing on animation start
            }
            override fun onAnimationRepeat(animation: Animation) {
                // do nothing on animation repeat --> does not repeat
            }
            // when animation finishes --> nav to startup splash
            override fun onAnimationEnd(animation: Animation) {
                val intent = Intent(this@SplashOnStart,StartupSplash::class.java) // create intent object
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP // flag clears the activity stack and makes the new activity the only one on the stack
                startActivity(intent) // start the activity
                finish() // finish the current activity from the call stack
            }
        })

        // Create interpolators for the animation
        val anticipateOvershootInterpolator = AnticipateOvershootInterpolator()
        val bounceInterpolator = OvershootInterpolator(0.8f)

        // Set the duration of the animation to 2000 milliseconds
        animation.duration = 2000

        // Create lists of interpolators
        val interpolators = listOf(
            anticipateOvershootInterpolator,
            bounceInterpolator
        )

        // Create lists of fractions
        val fractions = listOf(
            0f,
            0.5f
        )

        // Create a custom interpolator using the above interpolators and fractions
        val customInterpolator = CustomInterpolator(interpolators, fractions)
        // Set the animation's interpolator to the custom interpolator
        animation.interpolator = customInterpolator
        // Set the animation to repeat indefinitely
        animation.repeatCount = Animation.INFINITE
        // Start the animation on the aniSplash ImageView
        aniSplash.startAnimation(animation)
    }

    // Custom interpolator class that implements Interpolator
    class CustomInterpolator(private val interpolators: List<Interpolator>, private val fractions: List<Float>) :
        Interpolator {
        override fun getInterpolation(input: Float): Float {
            val size = interpolators.size
            val lastIndex = size - 1

            for (i in 0 until lastIndex) {
                val startFraction = fractions[i]
                val endFraction = fractions[i + 1]

                // Check if the input is within the current fraction range
                if (input in startFraction..endFraction) {
                    val interpolator = interpolators[i]
                    // Calculate the interpolation value for the current fraction
                    val fraction = (input - startFraction) / (endFraction - startFraction)
                    return interpolator.getInterpolation(fraction)
                }
            }

            // If the input is outside all defined fractions, use the last interpolator
            return interpolators[lastIndex].getInterpolation(1f)
        }

    }
}