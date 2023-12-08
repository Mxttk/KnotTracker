package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*


class ProfileFragment : Fragment() {

    // vars
    private lateinit var firstNameText : TextView
    private lateinit var lastNameText : TextView
    private lateinit var recyclerFav : RecyclerView
    private lateinit var recyclerComplete : RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // logic for when view is created
        // Find the image views by their IDs from the fragment's layout
        firstNameText = view.findViewById(R.id.textViewFirstName)
        lastNameText = view.findViewById(R.id.textViewLastName)
        recyclerFav = view.findViewById(R.id.recyclerViewFavKnotsProfile)
        recyclerComplete = view.findViewById(R.id.recyclerViewCompleteKnotsProfile)

        recyclerFav.layoutManager = LinearLayoutManager(requireContext())
        recyclerFav.setHasFixedSize(true)

        recyclerComplete.layoutManager = LinearLayoutManager(requireContext())
        recyclerComplete.setHasFixedSize(true)

        val favoritesMap = mutableMapOf<String, KnotData>()
        val completedMap = mutableMapOf<String, KnotData>()
        recyclerFav.adapter = MyAdapter(requireContext(), favoritesMap)
        recyclerComplete.adapter = MyAdapter(requireContext(), favoritesMap)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference
        val userReference = databaseReference.child("userdata")
            .child("productionUsers")
            .child(User.userName.toString())

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retrieve first name and last name from the user's data
                val firstName = snapshot.child("firstName").getValue(String::class.java)
                val lastName = snapshot.child("lastName").getValue(String::class.java)

                // Update the TextViews with the retrieved data
                firstNameText.text = firstName
                lastNameText.text = lastName
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle the error if needed
            }
        })

        // grabbing user favourites list
        val userReferenceKnots  = databaseReference.child("userdata")
                                .child("productionUsers")
                                .child(User.userName.toString())
                                .child("myKnots")


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

                        when (knotData.completionStatus!!.toInt()){
                            3 -> {
                                completedMap[snap.key.toString()] = knotData
                            }
                        }
                    }
                }
                recyclerFav.adapter = context?.let { MyAdapter(it,favoritesMap) }
                recyclerComplete.adapter = context?.let { MyAdapter(it,completedMap) }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })
    }


}