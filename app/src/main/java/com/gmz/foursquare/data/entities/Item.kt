package com.gmz.foursquare.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
data class Item @Inject constructor(val name:String, val tipHint:String, val categories:List<Category>, val location:Location) :Parcelable
