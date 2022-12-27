package com.gmz.foursquare.data.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import javax.inject.Inject

@Parcelize
class Category @Inject constructor(var name:String) :Parcelable{
}