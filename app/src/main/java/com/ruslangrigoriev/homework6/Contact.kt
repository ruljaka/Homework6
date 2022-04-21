package com.ruslangrigoriev.homework6

import java.io.Serializable

data class Contact(
    val id: Int,
    var firstName: String,
    var lastName: String,
    var number: String,
    val imageUrl: String
) : Serializable {
    override fun toString(): String {
        return "$firstName $lastName"
    }
}


