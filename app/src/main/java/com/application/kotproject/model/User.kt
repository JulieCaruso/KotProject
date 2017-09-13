package com.application.kotproject.model

import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.TextInputEditText
import com.application.kotproject.utils.CryptoUtils

data class User(val firstname: String?,
                val lastname: String?,
                val username: String,
                private val password: String,
                private val sex: String?,
                val departmentid: Int,
                private val token: String?,
                private val id: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readString(),
            parcel.readInt())

    constructor(usernameInput: TextInputEditText, passwordInput: TextInputEditText) : this(
            null,
            null,
            usernameInput.text.toString(),
            CryptoUtils.encryptSHA1(passwordInput.text.toString()),
            null,
            -1,
            null,
            -1)

    constructor(firstnameInput: TextInputEditText, lastnameInput: TextInputEditText, usernameInput: TextInputEditText, passwordInput: TextInputEditText,
                sex: String, departmentid: Int) : this(
            firstnameInput.text.toString(),
            lastnameInput.text.toString(),
            usernameInput.text.toString(),
            CryptoUtils.encryptSHA1(passwordInput.text.toString()),
            sex,
            departmentid,
            "token",
            -1)

    constructor(firstnameInput: TextInputEditText, lastnameInput: TextInputEditText, usernameInput: TextInputEditText, passwordInput: TextInputEditText,
                sex: String, departmentid: Int, id: Int) : this(
            firstnameInput.text.toString(),
            lastnameInput.text.toString(),
            usernameInput.text.toString(),
            CryptoUtils.encryptSHA1(passwordInput.text.toString()),
            sex,
            departmentid,
            "token",
            id)

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(firstname)
        dest?.writeString(lastname)
        dest?.writeString(username)
        dest?.writeString(password)
        dest?.writeString(sex)
        dest?.writeInt(departmentid)
        dest?.writeString(token)
        dest?.writeInt(id)
    }

    override fun describeContents(): Int = 0

    companion object {

        val CREATOR = object : Parcelable.Creator<User> {
            override fun createFromParcel(parcel: Parcel): User = User(parcel)

            override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
        }
    }
}