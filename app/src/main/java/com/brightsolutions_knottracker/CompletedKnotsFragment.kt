package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class CompletedKnotsFragment : Fragment() {

    private lateinit var recyclerCompletedKnots : RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_completed_knots, container, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recyclerCompletedKnots = view.findViewById(R.id.recyclerViewCompletedKnotsFrag)
        recyclerCompletedKnots.layoutManager = LinearLayoutManager(requireContext())
        recyclerCompletedKnots.setHasFixedSize(true)

        val currentMap = mutableMapOf<String, KnotData>()
        recyclerCompletedKnots.adapter = MyAdapter(requireContext(), currentMap)

        //init db
        databaseReference = FirebaseDatabase.getInstance().reference

        // grabbing user complete list
        val userReferenceKnots  = databaseReference.child("userdata")
            .child("productionUsers")
            .child(User.userName.toString())
            .child("myKnots")

        userReferenceKnots.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshotUser in snapshot.children) {
                    for (snap in snapshotUser.children){
                        val knotData = snap.getValue(KnotData::class.java) as KnotData

                        // when knot is marked with completion status of 3 --> completed --> add to hash map with K and V
                        when (knotData.completionStatus!!.toInt()){
                            3 -> {
                                currentMap[snap.key.toString()] = knotData
                            }
                        }
                    }
                }
                recyclerCompletedKnots.adapter = MyAdapter(requireContext(),currentMap)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })

    }



}