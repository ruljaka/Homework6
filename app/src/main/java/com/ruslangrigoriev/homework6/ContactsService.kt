package com.ruslangrigoriev.homework6

import com.github.javafaker.Faker

class ContactsService {

    private val faker = Faker.instance()
    val contacts: List<Contact> = (1..100).map {
        Contact(
            id = it,
            firstName = faker.name().firstName(),
            lastName = faker.name().lastName(),
            number = faker.phoneNumber().cellPhone(),
            imageUrl = "https://picsum.photos/id/$it/400/600"
        )
    }
}