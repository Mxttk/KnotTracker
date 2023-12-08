package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class SimpleListItem : AppCompatActivity() {
    private lateinit var textViewDisplayTitle : TextView
    private lateinit var textViewDisplayBody : TextView
    private lateinit var textViewDisplayDifficulty : TextView
    private lateinit var imageViewCloseWindow : ImageView
    private lateinit var imageViewBeginCompletion : ImageView
    private lateinit var videoTut : VideoView
    private lateinit var mediaControllerForTut : MediaController

    // temp URL variable that will be used to set the video source
    private var onlineURL : String = ""

    private lateinit var databaseReference: DatabaseReference

    @SuppressLint("UseCompatLoadingForDrawables", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simple_list_item)

        val knotName = intent.getStringExtra("knotName")
        val knotCategory = intent.getStringExtra("knotCategory")
        val knotIsFavorite = intent.getStringExtra("knotIsFavorite")
        val knotDescription = intent.getStringExtra("knotDescription")
        val knotDifficulty = intent.getStringExtra("knotDifficulty")
        val knotCompletionStatus = intent.getStringExtra("knotCompletionStatus")
        val knotCompletedImgURL = intent.getStringExtra("knotCompletedImgURL")
        val knotThumbnailURL = intent.getStringExtra("knotThumbnailURL")
        val knotVideoURL = intent.getStringExtra("knotVideoURL")

        // Inside your SimpleListItem activity, after receiving intent data
        val currentItem = KnotData() // Create a new KnotData object

        // Set the properties of the currentItem
        currentItem.name = knotName
        currentItem.category = knotCategory
        currentItem.isFavourite = knotIsFavorite.toBoolean()
        currentItem.description = knotDescription
        currentItem.difficulty = knotDifficulty?.toLong()
        currentItem.completionStatus = knotCompletionStatus?.toLong()
        currentItem.completedImageUrl = knotCompletedImgURL
        currentItem.thumbnailUrl = knotThumbnailURL
        currentItem.videoUrl = knotVideoURL


        // typecasting
        textViewDisplayTitle = findViewById(R.id.textViewEntryTitlePopUp)
        textViewDisplayBody = findViewById(R.id.textViewEntryDescriptionPopUp)
        textViewDisplayDifficulty = findViewById(R.id.textViewEntryDifficultyPopUp)
        imageViewCloseWindow = findViewById(R.id.imageViewCloseWindow)
        imageViewBeginCompletion = findViewById(R.id.imageViewBeginCompletion)
        videoTut = findViewById(R.id.videoViewSimplePopUp)
        mediaControllerForTut = MediaController(this)
        mediaControllerForTut.elevation = 10F // raise media controller

        // set BG colour
        mediaControllerForTut.setBackgroundColor(getColor(R.color.scouts_purple))

        // set media controller
        videoTut.setMediaController(mediaControllerForTut)
        mediaControllerForTut.setAnchorView(videoTut) // anchor video

        databaseReference = FirebaseDatabase.getInstance().reference

        // grab the current logged in users favourite's list
        val userReference = databaseReference.child("userdata").child("productionUsers")
            .child(User.userName.toString()).child("myKnots")
        val knotsMap = mutableMapOf<String, KnotData>() // list of fav knots to hold current users fav knots

        /*
         Query execution:
        */

        // Fav Knots Query
        // populates map with all the knots that are favourite
        userReference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshotUser in snapshot.children) {
                    for (snap in snapshotUser.children){
                        knotsMap[snap.key.toString()] = snap.getValue(KnotData::class.java) as KnotData // add all fav list in Fb to the map
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SimpleListItem, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })


        when (currentItem.completionStatus?.toInt()) {
            0 ->{
                // not completed
                imageViewBeginCompletion.setImageResource(R.drawable.add_to_current)

            }
            1 -> {
                // currently completing
                imageViewBeginCompletion.setImageResource(R.drawable.in_progress)

            }
            2 -> {
                // to be completed
                imageViewBeginCompletion.setImageResource(R.drawable.add_to_current)
            }
            3 -> {
                // completed
                imageViewBeginCompletion.setImageResource(R.drawable.complete)
            }
        }

        imageViewBeginCompletion.setOnClickListener{
            when (currentItem.completionStatus?.toInt()) {
                0 ->{
                    // not completed
                    Toast.makeText(this,"Now tracking {$knotName}",Toast.LENGTH_SHORT).show()
                    imageViewBeginCompletion.setImageResource(R.drawable.in_progress)
                    currentItem.completionStatus = 1
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    // refresh items
                    imageViewBeginCompletion.invalidate()
                    val n = MyAdapter(this,knotsMap)
                    n.notifyDataSetChanged()
                }
                1 -> {
                    // currently completing
                   Toast.makeText(this,"Knot is already being completed",Toast.LENGTH_SHORT).show()
                }
                2 -> {
                    // to be completed
                    Toast.makeText(this,"Now tracking {$knotName}",Toast.LENGTH_SHORT).show()
                    imageViewBeginCompletion.setImageResource(R.drawable.in_progress)
                    currentItem.completionStatus = 1
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    // refresh items
                    imageViewBeginCompletion.invalidate()
                    val n = MyAdapter(this,knotsMap)
                    n.notifyDataSetChanged()
                }
                3 -> {
                    // completed
                    Toast.makeText(this,"Knot already has been completed!",Toast.LENGTH_SHORT).show()
                }
            }
        }

        when (knotCategory) {
            "The 6 basic knots" -> {
                // switch statement for when diff knots have been selected -> relative URL will be selected
                when (knotName) {
                    "Bowline" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/The%206%20Basic%20Knots%2FBowline.mp4?alt=media&token=a80ff2b8-7928-4edf-a023-309d31b29a9e"
                    }
                    "Clove Hitch" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/The%206%20Basic%20Knots%2FClove%20Hitch.mp4?alt=media&token=c9163666-3895-4ffc-ae17-3b254fb4d2e3"
                    }
                    "Reef Knot" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/The%206%20Basic%20Knots%2FReef%20Knot.mp4?alt=media&token=dc5bd971-7a24-498d-899b-d2897a8600de"
                    }
                    "Round Turn and Two Half Hitches" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/The%206%20Basic%20Knots%2FRound%20Turn%20%26%20Two%20Half%20Hitches.mp4?alt=media&token=87445457-5c92-455d-82d2-680d37e5d85b"
                    }
                    "Sheepshank" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/The%206%20Basic%20Knots%2FSheepshank.mp4?alt=media&token=7d16bcf2-e9db-4658-b20e-413194d70666"
                    }
                    "Sheet Bend" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/The%206%20Basic%20Knots%2FSheetbend.mp4?alt=media&token=105d8138-4d67-467e-b34a-bea6dcc30eb2"
                    }
                    else -> {
                        // will pass dummy vid here for cases when vid doesn't load / URL cannot be found
                        onlineURL = ""
                    }
                }
            }
            "Hitches" -> {
                when (knotName) {
                    "Marline Spike Hitch" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Hitches%2FMarlinspike%20Hitch.mp4?alt=media&token=79c67133-281e-468d-ab8d-8a6d21813e74"
                    }
                    "Rolling Hitch" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Hitches%2FRolling%20Hitch.mp4?alt=media&token=19b25701-5ed5-4416-a851-03be525f7ec7"
                    }
                    "Taut Line Hitch" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Hitches%2FTaut%20Line%20Hitch.mp4?alt=media&token=53e9431b-4d55-4d5c-a9a0-1043e92a747d"
                    }
                    "Timber Hitch" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Hitches%2FTimber%20Hitch.mp4?alt=media&token=76d77c5e-a485-4b89-ae36-0c299c3878c1"
                    }
                    else -> {
                        // will pass dummy vid here for cases when vid doesn't load / URL cannot be found
                        onlineURL = ""
                    }
                }
            }
            "General" -> {
                // switch statement for when diff knots have been selected -> relative URL will be selected
                when (knotName) {
                    "Bowline on a Bite" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/General%20Knots%2FBowline%20On%20A%20Bight.mp4?alt=media&token=5a3a0c8b-d6af-46f1-891b-b87c7887b88d"
                    }
                    "Carrick Bend" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/General%20Knots%2FCarrick%20Bend.mp4?alt=media&token=f850196a-5806-44d8-863e-195a28274f2d"
                    }
                    "Figure of 8" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/General%20Knots%2FFigure%20Of%208.mp4?alt=media&token=f6b467ae-633a-4842-95ba-24d1de062d1c"
                    }
                    "Fisherman's Knot" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/General%20Knots%2FFisherman's%20Knot.mp4?alt=media&token=738be12e-8662-4d2a-b863-3eb1c35b60b5"
                    }
                    else -> {
                        // will pass dummy vid here for cases when vid doesn't load / URL cannot be found
                        onlineURL = ""
                    }
                }
            }
            "Lashings" -> {
                // switch statement for when diff knots have been selected -> relative URL will be selected
                when (knotName) {
                    "Diagonal Lashing" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Lashings%2FDiagonal%20Lashing.mp4?alt=media&token=8c616f17-ee46-4bfc-b41d-7e0b5e886d30"
                    }
                    "Figure of Eight Lashing" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Lashings%2FFigure%20Of%208%20Lashing.mp4?alt=media&token=847c20fd-944e-4a1c-83f7-e009dbdec879"
                    }
                    "Round Lashing" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Lashings%2FRound%20Lashing.mp4?alt=media&token=ff59018e-8a48-4180-91a7-145ae5add7f5"
                    }
                    "Shear Lashing" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Lashings%2FShear%20Lashing.mp4?alt=media&token=6c82943e-1f9e-45a0-954c-25bb0bdcbfd2"
                    }
                    "Square Lashing" -> {
                        onlineURL = "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Lashings%2FSquare%20Lashing.mp4?alt=media&token=cde684bc-7bfe-49c7-972e-5db41f5ffb53"
                    }
                    else -> {
                        // will pass dummy vid here for cases when vid doesn't load / URL cannot be found
                        onlineURL = ""
                    }
                }
            }
            "Whipping" -> {
                // switch statement for when diff knots have been selected -> relative URL will be selected
                onlineURL = when (knotName) {
                    "Sailmaker's Whipping" -> {
                        "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Whipping%2FSailmaker's%20Whipping.mp4?alt=media&token=5544b1db-3b4b-4709-89d3-b95c51ff78c3"
                    }
                    "Simple Whipping" -> {
                        "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Whipping%2FSimple%20Whipping.mp4?alt=media&token=e523c4fd-21e4-41a7-ae56-d3758905e5ba"
                    }
                    "West Country Whipping" -> {
                        "https://firebasestorage.googleapis.com/v0/b/brightsolutions-knottracker.appspot.com/o/Whipping%2FWest%20Country%20Whipping.mp4?alt=media&token=c3e142cc-5374-4b6e-a8b3-868011f0ce5c"
                    }
                    else -> {
                        // will pass dummy vid here for cases when vid doesn't load / URL cannot be found
                        ""
                    }
                }
            }
        }

        // Online video URL --> will be dependant on knot selected
        val onlineUri = Uri.parse(onlineURL)
        videoTut.setVideoURI(onlineUri)

        // start the vid playing
        videoTut.start()

        // listener for when error occurs
        videoTut.setOnErrorListener { _, _, _ ->
            // Handle video playback errors
            true // Return true to indicate that you've handled the error
        }

        val entryTitle = StringBuilder()
        entryTitle.append(knotName)

        val entryDetails = StringBuilder()
        entryDetails.append("\n").append(knotDescription)

        val difficulty = StringBuilder()
        difficulty.append("\n").append("Difficulty rating: ").append(knotDifficulty)

        // set title and body text to saved string
        textViewDisplayTitle.text = entryTitle.toString()
        textViewDisplayBody.text = entryDetails.toString()
        textViewDisplayDifficulty.text = difficulty.toString()

        imageViewCloseWindow.setOnClickListener{
            // closes view to send user back to where they were before opening this list item
            finish()
        }
    }

}