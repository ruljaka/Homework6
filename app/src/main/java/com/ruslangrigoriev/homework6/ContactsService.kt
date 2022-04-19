package com.ruslangrigoriev.homework6

import com.github.javafaker.Faker

class ContactsService {

    private val faker = Faker.instance()
    val contacts: MutableList<Contact> = (1..150).map {
        Contact(
            id = it,
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            number = faker.phoneNumber().cellPhone(),
            imageUrl = "https://picsum.photos/id/$it/400/600"
        )
    }.toMutableList()

    fun deleteContact(contact: Contact) {
        contacts.remove(contact)
    }
}