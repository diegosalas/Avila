package com.vectormobile.fundacionavila.models

import android.os.Parcel
import android.os.Parcelable


data class Direction(var latitude: String, var longitude: String) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(latitude)
        parcel.writeString(longitude)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Direction> {
        override fun createFromParcel(parcel: Parcel): Direction {
            return Direction(parcel)
        }

        override fun newArray(size: Int): Array<Direction?> {
            return arrayOfNulls(size)
        }
    }

}