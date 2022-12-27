package com.gmz.foursquare.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class Venues @Inject constructor(val count:Integer, val items:List<Item>):Parcelable
