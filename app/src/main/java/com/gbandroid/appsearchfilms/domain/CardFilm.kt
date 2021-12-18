package com.gbandroid.appsearchfilms.domain

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
