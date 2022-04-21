package com.ruslangrigoriev.homework6

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruslangrigoriev.homework6.DialogFragment.Companion.DIALOG_REQUEST_KEY
import com.ruslangrigoriev.homework6.DialogFragment.Companion.DIALOG_TAG
import com.ruslangrigoriev.homework6.databinding.FragmentContactsListBinding


class ContactsListFragment : Fragment(R.layout.fragment_contacts_list) {

    private var _binding: FragmentContactsListBinding? = null
    private val binding get() = _binding!!
    private var contract: FragmentContract? = null
    private var contactsList: MutableList<Contact>? = null
    private lateinit var adapter: ContactListAdapter

    companion object {
        const val FRAGMENT_LIST_TAG = "FRAGMENT_LIST_TAG"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentContract) {
            contract = context
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentContactsListBinding.bind(view)

        contactsList = contract?.getContactsService()?.contacts?.toMutableList()
        with(binding) {
            adapter = ContactListAdapter({ onListItemClick(it) }, { onListItemLongClick(it) })
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            adapter.submitList(contactsList)
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    contactsList?.let { adapter.filter(query, it) }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    contactsList?.let { adapter.filter(newText, it) }
                    return false
                }
            })
        }
    }

    private fun onListItemClick(contact: Contact) {
        contract?.fromListToDetails(contact)
    }

    private fun onListItemLongClick(contact: Contact): Boolean {
        childFragmentManager.setFragmentResultListener(DIALOG_REQUEST_KEY, this) { _, _ ->
            contract?.getContactsService()?.deleteContact(contact)
            contract?.getContactsService()?.contacts?.let {
                contactsList = ArrayList(it)
                adapter.submitList(contactsList)
            }
        }
        val dialog = DialogFragment()
        dialog.show(childFragmentManager, DIALOG_TAG)
        return true
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

