@file:Suppress("unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused", "unused",
    "unused", "unused", "unused", "unused"
)

package com.brightsolutions_knottracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

@Suppress("UNUSED_PARAMETER", "unused")
class FavouriteList : AppCompatActivity() {
    private lateinit var recyclerFavKnots : RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favourite_list)

        recyclerFavKnots = findViewById(R.id.recyclerViewFavKnots)

        recyclerFavKnots.layoutManager = LinearLayoutManager(this)
        recyclerFavKnots.setHasFixedSize(true)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference
        // grabbing user favourites list
        val userReferenceKnots  = databaseReference.child("userdata")
            .child("productionUsers")
            .child(User.userName.toString())
            .child("myKnots")

        val favoritesMap = mutableMapOf<String, KnotData>()
        recyclerFavKnots.adapter = MyAdapter(context = this@FavouriteList,favoritesMap)

        userReferenceKnots.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshotUser in snapshot.children) {
                    for (snap in snapshotUser.children){
                        val knotData = snap.getValue(KnotData::class.java) as KnotData

                        when (knotData.isFavourite){
                            true -> {
                                favoritesMap[snap.key.toString()] = knotData // add knot item to map
                            }
                            else -> {
                                // do nothing
                            }
                        }
                    }
                }
                recyclerFavKnots.adapter = MyAdapter(context = this@FavouriteList,favoritesMap)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@FavouriteList, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun closeWindow(view: View) {
        finish()
    }
}