package com.brightsolutions_knottracker

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class SeedUser {
    private val database = FirebaseDatabase.getInstance().reference
    // block is called on class object initialisation
    init {
        seedDummyUser()
    }

    private fun seedDummyUser() {
        // seeds the entire list of knots to users path
        val knotReference = database.child("userdata").child("testUsers").child("dummyUser").child("myKnots")

        val knotEntries = mapOf(
            "KnotList" to listOf(
                KnotData(
                    name = "Bowline",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A loop knot that creates a secure and fixed loop at the end of a rope. Widely used for various applications, including rescue scenarios.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "The 6 basic knots",
                    isFavourite = false
                ),
                KnotData(
                    name = "Clove Hitch",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = " A quick and easy knot used to temporarily secure a rope to a post, pole, or other objects.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "The 6 basic knots",
                    isFavourite = false
                ),
                KnotData(
                    name = "Reef Knot",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A simple knot used to join two ends of a single rope or two ropes of similar diameter. Useful for tying parcels or bundling items as well as a sling for medical aid",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "The 6 basic knots",
                    isFavourite = false
                ),
                KnotData(
                    name = "Sheepshank",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A knot used to temporarily shorten a rope while maintaining the ability to quickly release it. It prevents kinking when slack is taken up.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "The 6 basic knots",
                    isFavourite = false
                ),
                KnotData(
                    name = "Sheet Bend",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = " Used for joining two ropes of different diameters. It's a reliable knot for this purpose.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "The 6 basic knots",
                    isFavourite = false
                ),
                KnotData(
                    name = "Round Turn and Two Half Hitches",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A versatile knot used for securing a rope to a post or ring. Provides stability under load.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "The 6 basic knots",
                    isFavourite = false
                ),
                KnotData(
                    name = "Square Lashing",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "Used to bind two poles together that are perpendicular to each other. It's a basic lashing technique suitable for various projects.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Lashings",
                    isFavourite = false
                ),
                KnotData(
                    name = "Shear Lashing",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "Primarily used to bind two poles together when they are parallel and close to each other.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Lashings",
                    isFavourite = false
                ),
                KnotData(
                    name = "Figure of Eight Lashing",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = " Used to bind two poles together at right angles, creating a sturdy connection.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Lashings",
                    isFavourite = false
                ),
                KnotData(
                    name = "Diagonal Lashing",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = " A lashing used to bind two poles together diagonally. It's strong and stable, often used in construction projects.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Lashings",
                    isFavourite = false
                ),
                KnotData(
                    name = "Round Lashing",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A lashing used to secure two poles together in a parallel or crossed arrangement.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Lashings",
                    isFavourite = false
                ),
                KnotData(
                    name = "Simple Whipping",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "Similar to common whipping, it's a basic method to protect the rope's end from fraying",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Whipping",
                    isFavourite = false
                ),
                KnotData(
                    name = "Sailmaker's Whipping",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A more elaborate form of whipping, often used on the ends of ropes that will undergo heavy wear.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Whipping",
                    isFavourite = false
                ),
                KnotData(
                    name = "West Country Whipping",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A decorative and functional whipping used to secure the ends of ropes and prevent fraying.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Whipping",
                    isFavourite = false
                ),
                KnotData(
                    name = "Figure of 8",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A stopper knot that prevents a rope from passing through a hole or a device. It's often used at the end of a rope to prevent fraying or as a visual indicator of the rope's end.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "General",
                    isFavourite = false
                ),
                KnotData(
                    name = "Fisherman's Knot",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = " A bend knot used to join two ropes of similar diameter. It's secure and reliable but can be difficult to untie after heavy loading.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "General",
                    isFavourite = false
                ),
                KnotData(
                    name = "Bowline on a Bite",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "Similar to the bowline but tied in the middle of a rope, creating two loops. It's useful for creating secure loops without access to the rope's ends.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "General",
                    isFavourite = false
                ),
                KnotData(
                    name = "Carrick Bend",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A knot used to join two ropes together. It's less likely to jam compared to other bend knots, making it useful for heavy loads.",
                    difficulty = 3,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "General",
                    isFavourite = false
                ),
                KnotData(
                    name = "Marline Spike Hitch",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A simple knot where the working end is passed around an object and then tucked under itself. It's used as a starting point for more secure knots or to prevent a line from slipping temporarily.",
                    difficulty = 1,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Hitches",
                    isFavourite = false
                ),
                KnotData(
                    name = "Rolling Hitch",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "A knot used to attach a rope to another rope or a cylindrical object under tension. It grips well and can slide along the object when pulled, making it useful in situations where tension may change.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Hitches",
                    isFavourite = false
                ),
                KnotData(
                    name = "Taut Line Hitch",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "This adjustable hitch is commonly used for tent guy lines. It can be easily tensioned or loosened to adapt to changes in tension, making it ideal for situations where the line needs to be adjustable.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Hitches",
                    isFavourite = false
                ),
                KnotData(
                    name = "Timber Hitch",
                    thumbnailUrl = "URL_to_thumbnail_image",
                    completedImageUrl = "URL_to_completed_image",
                    description = "Primarily used for hauling logs or timber, the timber hitch grips well under tension and doesn't jam, making it suitable for dragging heavy objects.",
                    difficulty = 2,
                    videoUrl = "URL_to_video_tutorial",
                    completionStatus = 0,
                    category = "Hitches",
                    isFavourite = false
                )
            )
        )

        knotReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for ((categoryName, knotsInCategory) in knotEntries) {
                    val categoryReference = knotReference.child(categoryName)
                    for (knot in knotsInCategory) {
                        if (!snapshot.child(categoryName).hasChild(knot.name!!)) {
                            categoryReference.child(knot.name!!).setValue(knot)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                return
            }
        })
    }
}