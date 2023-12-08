package com.brightsolutions_knottracker

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CreateUserData {
    // DB ref
    private val database = FirebaseDatabase.getInstance().reference

    fun createUserDataOnReg(usernameTemp: String, userFirstname : String, userLastName: String, userEmail : String){
        val userReference = database.child("userdata") // ref path for user creation

        // Create mutable maps of KnotData for the users favourites, completed and completing lists --> will be created with empty values as the user cannot have data yet
        val myMap = mutableMapOf<String, KnotData>()

        // map of users to
        val userEntries = mapOf(
            "productionUsers" to listOf(
                UserData(
                    userName = usernameTemp, // takes email address from args in method call
                    emailAddress = userEmail,
                    firstName = userFirstname,
                    lastName = userLastName,
                    profilePicUrl = "https://cdn.pixabay.com/photo/2016/08/31/11/54/icon-1633249_1280.png", // default profile pic
                    myKnots = myMap
                )
            )
        )

        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ((userName, userDetails) in userEntries) {
                    val useref = userReference.child(userName)
                    for (user in userDetails) {
                        if (!snapshot.child(userName).hasChild(user.userName!!)) {
                            useref.child(user.userName).setValue(user)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}