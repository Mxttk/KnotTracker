package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*


class HomeFragment : Fragment() {

    private lateinit var firstNameText : TextView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // logic for when view is created
        // Find the image views by their IDs from the fragment's layout
        firstNameText = view.findViewById(R.id.textViewWelcomeUser)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference
        val userReference  = databaseReference.child("userdata").child("productionUsers").child(User.userName.toString())
        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                // Retrieve first name and last name from the user's data
                val firstName = snapshot.child("firstName").getValue(String::class.java)

                // Update the TextViews with the retrieved data
                firstNameText.text = "Welcome, $firstName!"
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })


    }

}