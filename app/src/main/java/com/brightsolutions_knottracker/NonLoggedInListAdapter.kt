package com.brightsolutions_knottracker

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class NonLoggedInListAdapter(private val context: Context, private var listOfEntries : MutableMap<String,KnotData>): RecyclerView.Adapter<NonLoggedInListAdapter.FavViewHolder>() {

    class FavViewHolder(view : View) : RecyclerView.ViewHolder(view)
    {
        val entryName : TextView = itemView.findViewById(R.id.textViewEntryTitle) // var targeting entry name ui element
        val entryDescrip : TextView = itemView.findViewById(R.id.textViewEntryDescription) // var targeting entry description ui element
        val knotIcon : ImageView = itemView.findViewById(R.id.imageViewKnotIcon) // icon var targeting knot mini icon ui element
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.recyler_item, parent, false)
        return FavViewHolder(itemView)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: FavViewHolder, position: Int) {

        // have to handle it as a hashmap
        val currentItem = listOfEntries.values.toList()[position]

        holder.entryName.text = currentItem.name
        holder.entryDescrip.text = currentItem.description

        // setting the knot images to their respective icons
        when (currentItem.name) {
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


        // creating on click for items
        holder.itemView.setOnClickListener{
            val intent = Intent(context, SimpleListItemNonLoggedIn::class.java)
            intent.putExtra("knotName", currentItem.name.toString())
            intent.putExtra("knotCategory", currentItem.category.toString())
            intent.putExtra("knotDescription", currentItem.description.toString())
            intent.putExtra("knotDifficulty", currentItem.difficulty.toString())

            // Add other necessary data as extras
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return listOfEntries.size
    }
}