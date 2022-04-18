package com.ruslangrigoriev.homework6

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), FragmentContract {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().run {
                val fragment = ContactsListFragment.newInstance()
                add(R.id.activity_frame_layout, fragment, ContactsListFragment.FRAGMENT_LIST_TAG)
                commit()
            }
        }
    }

    override fun getContactsService() =
        (applicationContext as App).contactService

    override fun fromListToDetails(contact: Contact) {
        supportFragmentManager.beginTransaction().run {
            val fragment = ContactDetailsFragment.newInstance(contact)
            replace(
                R.id.activity_frame_layout,
                fragment,
                ContactDetailsFragment.FRAGMENT_DETAILS_TAG
            )
            addToBackStack(ContactDetailsFragment.FRAGMENT_DETAILS_TAG)
            commit()
        }
    }

    override fun fromDetailsToList(contact: Contact) {
        supportFragmentManager.popBackStack()
        supportFragmentManager.beginTransaction().run {
            val fragment = ContactsListFragment.newInstance(contact)
            replace(R.id.activity_frame_layout, fragment, ContactsListFragment.FRAGMENT_LIST_TAG)
            commit()
        }
    }
}