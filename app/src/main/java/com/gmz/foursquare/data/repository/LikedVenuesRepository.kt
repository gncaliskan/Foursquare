package com.gmz.foursquare.data.repository

import com.gmz.foursquare.data.api.DataService
import com.gmz.foursquare.data.entities.Item
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class LikedVenuesRepository {
    val dataService = DataService.getDataService()
    lateinit var likedVenuesFlow : Flow<List<Item>>


    fun getUserLikedVenues(token:String, version:String): Flow<List<Item>> {
        likedVenuesFlow = flow {
            var likedVenues = dataService.getLikedVenues(token , version)
            likedVenues.body()?.let {
                if (likedVenues.isSuccessful) {
                    likedVenues.body().let {
                        var items = likedVenues.body()!!.response.venues.items
                        emit(items)
                    }
                }
            }
        }
        return likedVenuesFlow
    }

}