package com.gmz.foursquare.viewModel.likedVenues

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class LikedVenuesViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LikedVenuesViewModel::class.java)) {
            return LikedVenuesViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown view model class")
    }
}
