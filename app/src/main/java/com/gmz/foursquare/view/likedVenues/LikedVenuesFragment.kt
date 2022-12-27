package com.gmz.foursquare.view.likedVenues

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.gmz.foursquare.data.entities.Item
import com.gmz.foursquare.databinding.FragmentLikedVenuesBinding
import com.gmz.foursquare.viewModel.likedVenues.LikedVenuesViewModel
import com.gmz.foursquare.viewModel.likedVenues.LikedVenuesViewModelFactory
import com.gmz.foursquare.viewModel.login.LoginViewModel
import com.gmz.foursquare.viewModel.login.LoginViewModelFactory

class LikedVenuesFragment : Fragment() {
    private lateinit var binding: FragmentLikedVenuesBinding
    private lateinit var likedVenuesViewModel: LikedVenuesViewModel
    private lateinit var likedVenuesAdapter: LikedVenuesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLikedVenuesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        createViewModels()
        getLikedVenues()
    }

    private fun getLikedVenues() {
        likedVenuesViewModel.getLikedVenues()
        addLikedVenuesListener()
    }

    private fun addLikedVenuesListener() {
        likedVenuesViewModel.collectedVenues.observe(this) { venueList ->
            if (venueList.isNotEmpty()) {
                configureRecycler(venueList as ArrayList<Item>)
            }
        }
    }

    private fun configureRecycler(venueList: ArrayList<Item>) {
        binding.rvLikedVenuesList.apply {
            layoutManager = LinearLayoutManager(context)
            likedVenuesAdapter = LikedVenuesAdapter()
            adapter = likedVenuesAdapter
            likedVenuesAdapter.venues = venueList
            likedVenuesAdapter.onItemClick = { venue->
                selectVenue(venue)
            }
        }
    }

    private fun selectVenue(item: Item) {
        findNavController().navigate(LikedVenuesFragmentDirections.actionListToVenueDetail(item))
    }

    private fun createViewModels() {
        activity?.let {
            likedVenuesViewModel =
                ViewModelProvider(
                    this,
                    LikedVenuesViewModelFactory(it.application)
                ).get(LikedVenuesViewModel::class.java)
        }
    }

}