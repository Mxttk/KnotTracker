package com.brightsolutions_knottracker

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView

private lateinit var drawerLayout: DrawerLayout
private lateinit var bottomNavigationView: BottomNavigationView
private lateinit var fragmentManagerVar: FragmentManager
private lateinit var floatingActionButton: FloatingActionButton

@Suppress("DEPRECATION", "UNUSED_VARIABLE")
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Create an instance of Seeding to initiate database seeding on app startup ->
        // code init block will execute seeding without need for object execution
        Seeding()

        floatingActionButton = findViewById(R.id.fab)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView: NavigationView = findViewById(R.id.navigation_drawer)
        navigationView.setNavigationItemSelectedListener(this)

        bottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigationView.background = null

        bottomNavigationView.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.bottom_home -> {
                        openFragment(HomeFragment())
                        return true
                    }
                    R.id.bottom_knots -> {
                        openFragment(ListOfKnotsFragment())
                        return true
                    }
                    R.id.bottom_about -> {
                        openFragment(AboutFragment())
                        return true
                    }
                    R.id.bottom_profile -> {
                        openFragment(ProfileFragment())
                        return true
                    }
                    else -> return false
                }

            }
        })

        fragmentManagerVar = supportFragmentManager
        openFragment(HomeFragment())

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, FavouriteList::class.java) // create intent object for back to startup splash
            startActivity(intent) // begin intent object
        }
    }




    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.nav_current_knots -> {
                openFragment(CurrentKnotsFragment())
            }
            R.id.nav_knot_goals -> {
                openFragment(GoalKnotsFragment())
            }
            R.id.nav_completed_knots -> {
                openFragment(CompletedKnotsFragment())
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }


}