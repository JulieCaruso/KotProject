package com.application.kotproject.model

import android.os.Parcel
import android.os.Parcelable
import android.support.design.widget.TextInputEditText

data class Department(val name: String,
                      private val address: String,
                      private val employeenb: Int,
                      val id: Int) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readInt())

    constructor(nameInput: TextInputEditText, addressInput: TextInputEditText, employeeNbInput: TextInputEditText) : this(
            nameInput.getText().toString(),
            addressInput.getText().toString(),
            getEmployeeNbFromInput(employeeNbInput),
            -1)

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeInt(employeenb)
        parcel.writeInt(id)
    }

    override fun describeContents(): Int = 0

    override fun toString(): String = this.name

    companion object {

        fun getEmployeeNbFromInput(employeeNbInput: TextInputEditText): Int {
            val employeeNbString = employeeNbInput.getText().toString()
            var nb: Int
            try {
                nb = Integer.parseInt(employeeNbString)
            } catch (e: NumberFormatException) {
                nb = 0
            }
            return nb
        }

        val CREATOR = object : Parcelable.Creator<Department> {
            override fun createFromParcel(parcel: Parcel): Department = Department(parcel)

            override fun newArray(size: Int): Array<Department?> = arrayOfNulls(size)
        }
    }
}