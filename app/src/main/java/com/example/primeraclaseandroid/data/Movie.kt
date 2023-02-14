package com.example.primeraclaseandroid.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id:Int,
    val title:String,
    val poster_path:String,
    val release_date: String?,
    val overview: String,
    val vote_average: Float
) : Parcelable