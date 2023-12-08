package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

// will need to pass in the users favourite list for seeding filled/non-filled hearts -->
class MyAdapter(private val context: Context, private var listOfEntries : MutableMap<String,KnotData>) :
    RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    private lateinit var databaseReference: DatabaseReference

    /*
   Completion Status cheat sheet:
   0 = not completed
   1 = marked to be completed / wish listing the knot
   2 = currently completing
   3 = completed
 */

    // code ref for how to create adapter : https://www.youtube.com/watch?v=VVXKVFyYQdQ&t=3s
    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val entryName: TextView =
            itemView.findViewById(R.id.textViewEntryTitle) // var targeting entry name ui element
        val entryDescrip: TextView =
            itemView.findViewById(R.id.textViewEntryDescription) // var targeting entry description ui element
        val knotIcon: ImageView =
            itemView.findViewById(R.id.imageViewKnotIcon) // icon var targeting knot mini icon ui element
        val knotIsFavourite: ImageView =
            itemView.findViewById(R.id.imgViewIsFav) // icon var targeting heart ui element
        val knotIsComplete: ImageView =
            itemView.findViewById(R.id.imgViewIsComplete) // icon var targeting complete ui element
        val knotIsOnGoalList: ImageView =
            itemView.findViewById(R.id.imgViewOnGoalList) // icon var targeting complete ui element
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.recyler_item, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n", "NotifyDataSetChanged")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // have to handle it as a hashmap
        val currentItem = listOfEntries.values.toList()[position]

        // init icons
        if (currentItem.isFavourite){
            holder.knotIsFavourite.setImageResource(R.drawable.heart_filled)
        }
        else
        {
            holder.knotIsFavourite.setImageResource(R.drawable.heart)
        }

        when (currentItem.completionStatus!!.toInt()) {
            0 -> {
                holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                holder.knotIsComplete.setImageResource(R.drawable.check_empty)
            }
            1 -> {
                holder.knotIsComplete.setImageResource(R.drawable.check_empty)
                holder.knotIsComplete.setImageResource(R.drawable.playlist_started)
            }
            2 -> {
                holder.knotIsOnGoalList.setImageResource(R.drawable.confirm_on_list)
                holder.knotIsComplete.setImageResource(R.drawable.check_empty)
            }
            3 -> {
                holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                holder.knotIsComplete.setImageResource(R.drawable.check_full)
            }
        }

        val knotName = currentItem.name
        holder.entryName.text = knotName
        holder.entryDescrip.text = currentItem.description
        /*
            Image icon assignment section:
        */

        // setting the knot images to their respective icons
        when (knotName) {
            "Marline Spike Hitch" -> {
                holder.knotIcon.setImageResource(R.drawable.marline_spike) // set the icon to the respective knot
            }
            "Rolling Hitch" -> {
                holder.knotIcon.setImageResource(R.drawable.rolling_hitch)
            }
            "Taut Line Hitch" -> {
                holder.knotIcon.setImageResource(R.drawable.taut_line_hitch)
            }
            "Timber Hitch" -> {
                holder.knotIcon.setImageResource(R.drawable.timber_hitch)
            }
            "Bowline on a Bite" -> {
                holder.knotIcon.setImageResource(R.drawable.bowline_on_a_bite)
            }
            "Carrick Bend" -> {
                holder.knotIcon.setImageResource(R.drawable.carrick_bend)
            }
            "Figure of 8" -> {
                holder.knotIcon.setImageResource(R.drawable.figure_of_8)
            }
            "Fisherman's Knot" -> {
                holder.knotIcon.setImageResource(R.drawable.fishermans_knot)
            }
            "Diagonal Lashing" -> {
                holder.knotIcon.setImageResource(R.drawable.diagonal_lashing)
            }
            "Figure of Eight Lashing" -> {
                holder.knotIcon.setImageResource(R.drawable.figure_of_8_lashing)
            }
            "Round Lashing" -> {
                holder.knotIcon.setImageResource(R.drawable.round_lashing)
            }
            "Shear Lashing" -> {
                holder.knotIcon.setImageResource(R.drawable.shear_lashing)
            }
            "Square Lashing" -> {
                holder.knotIcon.setImageResource(R.drawable.square)
            }
            "Bowline" -> {
                holder.knotIcon.setImageResource(R.drawable.bowline)
            }
            "Clove Hitch" -> {
                holder.knotIcon.setImageResource(R.drawable.clove_hitch_list)
            }
            "Reef Knot" -> {
                holder.knotIcon.setImageResource(R.drawable.reef_knot_list)
            }
            "Round Turn and Two Half Hitches" -> {
                holder.knotIcon.setImageResource(R.drawable.round_turn_and_two_half_hitches)
            }
            "Sheepshank" -> {
                holder.knotIcon.setImageResource(R.drawable.sheepshank)
            }
            "Sheet Bend" -> {
                holder.knotIcon.setImageResource(R.drawable.sheet_bend)
            }
            "Sailmaker's Whipping" -> {
                holder.knotIcon.setImageResource(R.drawable.sailmakers_whipping)
            }
            "Simple Whipping" -> {
                holder.knotIcon.setImageResource(R.drawable.common_whipping)
            }
            "West Country Whipping" -> {
                holder.knotIcon.setImageResource(R.drawable.west_country_whipping)
            }
            else -> {
                // set dummy icon for when no associated icon is found / when the img doesn't load
            }
        }


        //init db
        databaseReference = FirebaseDatabase.getInstance().reference

        /*
         Queries and their respective hash maps section:
        */

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
                // check whether the list contains the current item
                // if the item is in the user's favorite list, display a filled heart icon, else show an empty heart --> section used for controlling displaying
                if (knotsMap.containsKey(knotName.toString())) {
                        if (currentItem.isFavourite){
                            holder.knotIsFavourite.setImageResource(R.drawable.heart_filled)
                        }
                        else
                        {
                            holder.knotIsFavourite.setImageResource(R.drawable.heart)
                        }

                        when (currentItem.completionStatus!!.toInt()) {
                            0 -> {
                                holder.knotIsComplete.setImageResource(R.drawable.check_empty)
                                holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                            }
                            1 -> {
                                holder.knotIsComplete.setImageResource(R.drawable.check_empty)
                                holder.knotIsOnGoalList.setImageResource(R.drawable.playlist_started)
                            }
                            2 -> {
                                holder.knotIsComplete.setImageResource(R.drawable.check_empty)
                                holder.knotIsOnGoalList.setImageResource(R.drawable.confirm_on_list)
                            }
                            3 -> {
                                holder.knotIsComplete.setImageResource(R.drawable.check_full)
                                holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                            }
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, "Data retrieval cancelled", Toast.LENGTH_SHORT).show()
            }
        })


        /*
            On-click listeners:
        */

        // Favorite on-click
        holder.knotIsFavourite.setOnClickListener {
            when {
                currentItem.isFavourite -> {
                    // if is favourite
                    holder.knotIsFavourite.setImageResource(R.drawable.heart) // remove fav icon
                    currentItem.isFavourite = false // set is fav to false
                    // now remove from FB fav list
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    showToast("Knot removed from favourites successfully! :)")
                    notifyDataSetChanged()
                }
                else -> {
                    // if isn't favourite
                    holder.knotIsFavourite.setImageResource(R.drawable.heart_filled) // add fav icon
                    currentItem.isFavourite = true
                    // now add to FB fav list
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    notifyDataSetChanged()
                }
            }
        }

        // Is complete on-click
        holder.knotIsComplete.setOnClickListener {
            when {
                currentItem.completionStatus!!.toInt() == 1 -> {
                    holder.knotIsComplete.setImageResource(R.drawable.check_full) // add fav icon
                    currentItem.completionStatus = 3 // 3 == complete
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    showToast("Well done on completing the knot! :)")
                    notifyDataSetChanged()
                }
                currentItem.completionStatus!!.toInt() == 2 -> {
                    holder.knotIsComplete.setImageResource(R.drawable.check_full) // add fav icon
                    currentItem.completionStatus = 3 // 3 == complete
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                    showToast("Nice job getting that one off your list! :)")
                    notifyDataSetChanged()
                }
                currentItem.completionStatus!!.toInt() == 3 -> {
                    holder.knotIsComplete.setImageResource(R.drawable.check_empty) // remove check icon
                    currentItem.completionStatus = 0 // 0 == not complete
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    showToast("Knot removed from completed list successfully :)")
                    notifyDataSetChanged()
                }
                currentItem.completionStatus!!.toInt() == 0 -> {
                    holder.knotIsComplete.setImageResource(R.drawable.check_full) // add fav icon
                    currentItem.completionStatus = 3 // 3 == complete
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    showToast("Well done on completing the knot! :)")
                    holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list) // reset as completed knots cant be on the goal list
                    notifyDataSetChanged()
                }
            }
        }

        // Is On Goal List on-click
        // now on click listener --> controls functionality
        holder.knotIsOnGoalList.setOnClickListener {
            when {
                currentItem.completionStatus!!.toInt() == 1 -> {
                    // knot is already being completed
                    holder.knotIsOnGoalList.setImageResource(R.drawable.playlist_started)
                    showToast("Knot is already being completed! :)")
                }
                currentItem.completionStatus!!.toInt() == 2 -> {
                    holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                    currentItem.completionStatus = 0 // 0 == not complete
                    // now remove from FB fav list
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    showToast("Knot removed from goal list successfully :)")
                    notifyDataSetChanged()
                }
                currentItem.completionStatus!!.toInt() == 3 -> {
                    holder.knotIsOnGoalList.setImageResource(R.drawable.add_to_list)
                    showToast("Completed knots can't be added to your goal list :(")
                }
                currentItem.completionStatus!!.toInt() == 0 -> {
                    // last else will be case 0 -> if isn't on goal list
                    holder.knotIsOnGoalList.setImageResource(R.drawable.confirm_on_list)
                    currentItem.completionStatus = 2 // 2 == to be completed
                    // now add to FB fav list
                    userReference.child(currentItem.category.toString()).child(currentItem.name.toString()).setValue(currentItem) // update list with updated map
                    showToast("Knot added to goal list successfully :)")
                    notifyDataSetChanged()
                }
            }
        }

        /*
            Item on-click logic handling section:
        */

        // creating on click for items
        holder.itemView.setOnClickListener {
            val intent = Intent(context, SimpleListItem::class.java)
            intent.putExtra("knotName", currentItem.name.toString())
            intent.putExtra("knotCategory", currentItem.category.toString())
            intent.putExtra("knotIsFavorite", currentItem.isFavourite.toString())
            intent.putExtra("knotDescription", currentItem.description.toString())
            intent.putExtra("knotDifficulty", currentItem.difficulty.toString())
            intent.putExtra("knotCompletionStatus", currentItem.completionStatus.toString())
            intent.putExtra("knotCompletedImgURL", currentItem.completedImageUrl.toString())
            intent.putExtra("knotThumbnailURL", currentItem.thumbnailUrl.toString())
            intent.putExtra("knotVideoURL", currentItem.videoUrl.toString())

            // Add other necessary data as extras
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return listOfEntries.size
    }

    // Define a method to show the toast on the UI thread
    private fun showToast(message: String) {
        // Check if you're on the UI thread
        if (Looper.myLooper() == Looper.getMainLooper()) {
            // If you're on the UI thread, you can show the toast directly
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        } else {
            // If you're on a background thread, post a Runnable to show the toast on the UI thread
            Handler(Looper.getMainLooper()).post {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
        }
    }
}