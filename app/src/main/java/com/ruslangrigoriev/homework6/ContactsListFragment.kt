package com.ruslangrigoriev.homework6

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruslangrigoriev.homework6.databinding.FragmentContactsListBinding


class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private var _binding: FragmentContactsListBinding? = null
    private val binding get() = _binding!!
    private var contract: FragmentContract? = null
    private lateinit var contactsList: List<Contact>
    private lateinit var adapter: ContactListAdapter

    companion object {
        const val FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG"
        private const val LIST_ARG = "LIST_ARG"

        fun newInstance() = ContactsListFragment()

        fun newInstance(contact: Contact) =
            ContactsListFragment().apply {
                arguments = bundleOf(LIST_ARG to contact) }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentContract) { contract = context }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentContactsListBinding.bind(view)

        initList()
        setupUI()
    }

    private fun initList() {
        contactsList = contract?.getContactsService()?.contacts ?: emptyList()
        arguments?.let { bundle ->
            val editedContact = bundle.getSerializable(LIST_ARG) as Contact
            contactsList.filter { it.id == editedContact.id }
                .map {
                    if (it.firstName != editedContact.firstName) it.firstName = editedContact.firstName
                    if (it.lastName != editedContact.lastName) it.lastName = editedContact.lastName
                    if (it.number != editedContact.number) it.number = editedContact.number
                }
        }
    }

    private fun setupUI() {
        with(binding) {
            adapter = ContactListAdapter { onListItemClick(it) }
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.submitList(contactsList)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    adapter.filter(query, contactsList)
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter(newText, contactsList)
                    return false
                }
            })
        }
    }

    private fun onListItemClick(contact: Contact) {
        contract?.fromListToDetails(contact)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}