package com.brightsolutions_knottracker

class User {
    // class that is used to statically save the current logged in users username
    constructor()
    constructor(userName: String?)
    {
        Companion.userName = userName
    }

    var userName: String?
        get() = Companion.userName
        set(userName)
        {
            Companion.userName = userName
        }

    companion object
    {
        var userName: String? = null
    }
}