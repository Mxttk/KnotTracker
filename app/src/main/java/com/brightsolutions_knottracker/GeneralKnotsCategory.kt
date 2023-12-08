package com.brightsolutions_knottracker

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

@Suppress("RemoveExplicitTypeArguments")
class GeneralKnotsCategory : AppCompatActivity() {

    private lateinit var recyclerViewGeneralKnots: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerViewList : ArrayList<KnotData>
    private lateinit var knotsMap : MutableMap<String,KnotData>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_general_knots_category)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference
        recyclerViewList = arrayListOf<KnotData>()

        // Set up the RecyclerView and its adapter
        // Initialize the RecyclerView
        // ref: https://www.youtube.com/watch?v=VVXKVFyYQdQ&t=3s
        recyclerViewGeneralKnots = findViewById(R.id.recyclerViewGeneralKnots)
        recyclerViewGeneralKnots.layoutManager = LinearLayoutManager(this)
        recyclerViewGeneralKnots.setHasFixedSize(true)

        knotsMap = mutableMapOf<String, KnotData>()
        recyclerViewGeneralKnots.adapter = MyAdapter(this, knotsMap)

        // populates list on startup of page
        fetchData()
    }

    // populates recycler view on activity launch
    private fun fetchData()
    {
        // query for retrieving 6 basic knots
        val query = databaseReference.child("userdata").child("productionUsers").child(User.userName.toString()).child("myKnots").child("General")

        query.addListenerForSingleValueEvent(object : ValueEventListener
        {
            override fun onDataChange(snapshot: DataSnapshot)
            {
                for (entrySnapshot in snapshot.children)
                {
                    val knotData = entrySnapshot.getValue(KnotData::class.java) as KnotData
                    knotsMap[entrySnapshot.key.toString()] = knotData
                }
                recyclerViewGeneralKnots.adapter = MyAdapter(this@GeneralKnotsCategory,knotsMap) // add knot map to adapter call
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@GeneralKnotsCategory, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }


        })
    }

    fun closeWindow(view: View) {
        finish()
    }
}