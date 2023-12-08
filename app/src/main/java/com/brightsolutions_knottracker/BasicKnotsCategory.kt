@file:Suppress("RemoveExplicitTypeArguments")

package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


@Suppress("unused", "UNUSED_PARAMETER")
class BasicKnotsCategory : AppCompatActivity() {

    private lateinit var recyclerViewBasicKnots: RecyclerView
    private lateinit var databaseReference: DatabaseReference
    private lateinit var recyclerViewList : ArrayList<KnotData>
    private lateinit var dialogBasicKnots: Dialog
    private lateinit var knotsMap : MutableMap<String, KnotData>
    private lateinit var adapter : MyAdapter

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_basic_knots_category)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference
        recyclerViewList = arrayListOf<KnotData>()


        // Set up the RecyclerView and its adapter
        // Initialize the RecyclerView
        // ref: https://www.youtube.com/watch?v=VVXKVFyYQdQ&t=3s
        recyclerViewBasicKnots = findViewById(R.id.recyclerViewBasicKnots)
        recyclerViewBasicKnots.layoutManager = LinearLayoutManager(this)
        recyclerViewBasicKnots.setHasFixedSize(true)

        knotsMap = mutableMapOf<String, KnotData>()
        adapter = MyAdapter(this, knotsMap)
        recyclerViewBasicKnots.adapter = adapter

        // populates list on startup of page
        fetchData()
        adapter.notifyDataSetChanged()
    }


    // populates recycler view on activity launch
    private fun fetchData()
    {
        // query for retrieving 6 basic knots
        val query = databaseReference.child("userdata").child("productionUsers").child(User.userName.toString()).child("myKnots").child("The 6 basic knots")
        query.addListenerForSingleValueEvent(object : ValueEventListener
        {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot)
            {
                for (entrySnapshot in snapshot.children)
                {
                    val knotData = entrySnapshot.getValue(KnotData::class.java) as KnotData

                    knotsMap[entrySnapshot.key.toString()] = knotData
                }
                adapter.notifyDataSetChanged() // update data
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@BasicKnotsCategory, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }


        })
    }

    // dismiss popup window
    fun closeWindow(view: View){
       finish()
    }

}