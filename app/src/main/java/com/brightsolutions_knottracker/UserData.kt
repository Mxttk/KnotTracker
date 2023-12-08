package com.brightsolutions_knottracker
// class will be used to store values for the user profile page
data class UserData (
    val userName: String? = "",
    val emailAddress: String? = "",
    val firstName: String? = "",
    val lastName: String? = "",
    val profilePicUrl: String? = "",
    val myKnots: MutableMap<String,KnotData>,
)