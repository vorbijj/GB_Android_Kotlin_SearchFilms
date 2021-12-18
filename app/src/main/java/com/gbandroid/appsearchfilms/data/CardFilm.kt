package com.gbandroid.appsearchfilms.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CardFilm(
    val picture: String,
    val name: String,
    val year: String,
    val rating: String,
    val id: Long
) : Parcelable
