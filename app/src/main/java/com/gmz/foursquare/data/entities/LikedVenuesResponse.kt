package com.gmz.foursquare.data.entities

import javax.inject.Inject

data class LikedVenuesResponse
    @Inject constructor(val meta: Meta, val response: Response)
