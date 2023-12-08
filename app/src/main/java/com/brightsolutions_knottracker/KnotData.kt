package com.brightsolutions_knottracker
// class that will be used to manipulate the data
data class KnotData(
    var completedImageUrl: String? = "",
    var description: String? = "",
    var difficulty: Long? = 0,
    var name: String? = "",
    var thumbnailUrl: String? = "",
    var videoUrl: String? = "",
    var completionStatus: Long? = 0,
    var category: String? = "",
    var isFavourite: Boolean = false
)

