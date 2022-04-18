package com.ruslangrigoriev.homework6

interface FragmentContract {
    fun getContactsService() : ContactsService
    fun fromListToDetails(contact: Contact)
    fun fromDetailsToList(contact: Contact)
}