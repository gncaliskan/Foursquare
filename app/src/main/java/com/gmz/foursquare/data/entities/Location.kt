package com.gmz.foursquare.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class Location @Inject constructor(val formattedAddress: List<String>):Parcelable
