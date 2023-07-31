package com.example.intership_android_test

import android.os.Parcel
import android.os.Parcelable
data class User(
    val name: String,
    val age: Int,
    var isStudent: Boolean = false,
    var description: String = ""
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readByte() != 0.toByte(),
        parcel.readString()!!
    )
    override fun writeToParcel(parcel: Parcel, flags: Int) {

        parcel.writeString(name)
        parcel.writeInt(age)
        parcel.writeByte(if (isStudent) 1 else 0)
        parcel.writeString(description)
    }
    override fun describeContents(): Int {

        return 0
    }
    companion object CREATOR : Parcelable.Creator<User> {

        override fun createFromParcel(parcel: Parcel): User {
            return User(parcel)
        }

        override fun newArray(size: Int): Array<User?> {

            return arrayOfNulls(size)
        }
    }

}
