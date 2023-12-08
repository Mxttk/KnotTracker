package com.brightsolutions_knottracker

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView

class ListOfKnotsFragment : Fragment() {

    private lateinit var imageViewBasicKnots: ImageView
    private lateinit var imageViewLashings: ImageView
    private lateinit var imageViewHitches: ImageView
    private lateinit var imageViewGeneralKnots: ImageView
    private lateinit var imageViewWhipping: ImageView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_list_of_knots, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Find the image views by their IDs from the fragment's layout
        imageViewBasicKnots = view.findViewById(R.id.imageViewSixBasicKnots)
        imageViewLashings = view.findViewById(R.id.imageViewLashingKnots)
        imageViewHitches = view.findViewById(R.id.imageViewHitchKnots)
        imageViewGeneralKnots = view.findViewById(R.id.imageViewGeneralKnots)
        imageViewWhipping = view.findViewById(R.id.imageViewWhippingKnots)

        imageViewBasicKnots.setOnClickListener{
            val intent = Intent(requireContext(), BasicKnotsCategory::class.java)
            startActivity(intent)
        }

        imageViewLashings.setOnClickListener{
            val intent = Intent(requireContext(), LashingKnotsCategory::class.java)
            startActivity(intent)
        }

        imageViewHitches.setOnClickListener{
            val intent = Intent(requireContext(), HitchKnotCategory::class.java)
            startActivity(intent)
        }

        imageViewGeneralKnots.setOnClickListener{
            val intent = Intent(requireContext(), GeneralKnotsCategory::class.java)
            startActivity(intent)
        }

        imageViewWhipping.setOnClickListener{
            val intent = Intent(requireContext(), WhippingKnotCategory::class.java)
            startActivity(intent)
        }


    }
}