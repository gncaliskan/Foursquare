package com.gmz.foursquare.view.venueDetail

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gmz.foursquare.data.entities.Item
import com.gmz.foursquare.databinding.FragmentVenueDetailBinding

class VenueDetailFragment : Fragment() {
    private lateinit var binding: FragmentVenueDetailBinding
    private var item: Item? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVenueDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        getParameters()
        showInfo()
    }

    private fun showInfo() {
        binding.tvVenueDetailName.text = item?.name
        binding.tvVenueDetailAddress.text = item?.location?.formattedAddress?.get(0)
        binding.tvVenueDetailCategory.text = item?.categories?.get(0)?.name
    }

    private fun getParameters() {
        arguments?.let {
            item = if (Build.VERSION.SDK_INT >= 33) {
                it.getParcelable("item", Item::class.java)
            } else {
                it.getParcelable<Item>("item")
            }
        }
    }
}