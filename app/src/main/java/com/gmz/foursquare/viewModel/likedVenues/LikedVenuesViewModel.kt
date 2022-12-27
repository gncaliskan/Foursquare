package com.gmz.foursquare.viewModel.likedVenues

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gmz.foursquare.data.shared.PreferencesManager
import com.gmz.foursquare.data.entities.Item
import com.gmz.foursquare.data.repository.LikedVenuesRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LikedVenuesViewModel @Inject constructor(application: Application) : AndroidViewModel(application) {
    var collectedVenues = MutableLiveData<List<Item>>()
    val preferencesManager = PreferencesManager(application)
    val repo = LikedVenuesRepository()

    companion object UrlComponents {
        const val VERSION = "20220722"
    }

    fun getLikedVenues() {
        viewModelScope.launch {
            preferencesManager.getToken().collect { it ->
                repo.getUserLikedVenues(it, VERSION).collect { _data ->
                    if (_data.isNotEmpty()) {
                        collectedVenues.value = _data
                    }
                }
            }
        }
    }
}