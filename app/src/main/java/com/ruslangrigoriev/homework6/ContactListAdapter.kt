package com.ruslangrigoriev.homework6

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.ruslangrigoriev.homework6.databinding.ItemContactBinding
import java.util.*

class ContactListAdapter(private val onItemClicked: (contact: Contact) -> Unit) :
    ListAdapter<Contact, ContactListAdapter.ViewHolder>(ContactDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun filter(query: String?, initialList: List<Contact>) {
        if (query.isNullOrEmpty()) {
            submitList(initialList)
        } else {
            val filteredList = mutableListOf<Contact>()
            val text = query.toString().trim().lowercase(Locale.getDefault())
            initialList.forEach {
                if (it.firstName.lowercase(Locale.ROOT).contains(text)
                    || it.lastName.lowercase(Locale.ROOT).contains(text)
                ) {
                    filteredList.add(it)
                }
            }
            submitList(filteredList)
        }
    }

    class ViewHolder(
        private val view: View,
        private val onItemClicked: (contact: Contact) -> Unit
    ) : RecyclerView.ViewHolder(view) {

        fun bind(contact: Contact) {
            val binding = ItemContactBinding.bind(view)
            with(binding) {
                fullnameTextView.text = "${contact.firstName} ${contact.lastName}"
                imageView.load(contact.imageUrl) {
                    placeholder(R.drawable.ic_contact_holder)
                    transformations(CircleCropTransformation())
                }
                root.setOnClickListener {
                    onItemClicked(contact)
                }
            }
        }
    }

    class ContactDiffCallBack : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Contact, newItem: Contact): Boolean {
            return oldItem == newItem
        }
    }

}