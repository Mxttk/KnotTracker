package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

@Suppress("RemoveExplicitTypeArguments", "UNUSED_PARAMETER")
class KnotList : AppCompatActivity() {
    // Class that will be used to display list of knots to user who has not been authenticated / created an account
    // vars
    private lateinit var recyclerViewAllKnots: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerViewListAllKnots : ArrayList<KnotData>
    private lateinit var knotsMap : MutableMap<String,KnotData>
    private lateinit var adapter : NonLoggedInListAdapter

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_knot_list)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference
        recyclerViewListAllKnots = arrayListOf<KnotData>()

        // Set up the RecyclerView and its adapter
        // Initialize the RecyclerView
        // ref: https://www.youtube.com/watch?v=VVXKVFyYQdQ&t=3s
        recyclerViewAllKnots = findViewById(R.id.recyclerViewAllKnotsKnotList)
        recyclerViewAllKnots.layoutManager = LinearLayoutManager(this)
        recyclerViewAllKnots.setHasFixedSize(true)

        // init empty map
        knotsMap = mutableMapOf<String, KnotData>()
        adapter = NonLoggedInListAdapter(this,knotsMap)
        recyclerViewAllKnots.adapter = adapter

        fetchData()
        adapter.notifyDataSetChanged()
    }

    // populates recycler view on activity launch
    @SuppressLint("NotifyDataSetChanged")
    private fun fetchData()
    {

        // grab the knots from each category
        val knotRefAll = databaseReference.child("userdata").child("testUsers").child("dummyUser").child("myKnots").child("KnotList")

        // populates map with all the basic knots
        knotRefAll.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshotUser in snapshot.children) {
                    knotsMap[snapshotUser.key.toString()] = snapshotUser.getValue(KnotData::class.java)!! // add all knots in Fb to the map
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@KnotList, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun closeWindow(view: View) {
        val intent = Intent(this@KnotList,StartupSplash::class.java)
        startActivity(intent)
        finish()
    }


}