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

class ContactListAdapter(
    private val onItemClicked: (contact: Contact) -> Unit,
    private val onItemLongClicked: (contact: Contact) -> Boolean,
) :
    ListAdapter<Contact, ContactListAdapter.ViewHolder>(ContactDiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)
        return ViewHolder(view, onItemClicked, onItemLongClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun filter(query: String?, initialList: MutableList<Contact>) {
        if (query.isNullOrEmpty()) {
            submitList(initialList)
        } else {
            val textQuery = query.toString().lowercase(Locale.getDefault()).trim()
            val filteredList = initialList.filter {
                it.firstName.lowercase(Locale.getDefault()).contains(textQuery)
                        || it.lastName.lowercase(Locale.getDefault()).contains(textQuery)
            }.toMutableList()
            submitList(filteredList)
        }
    }

    class ViewHolder(
        private val view: View,
        private val onItemClicked: (contact: Contact) -> Unit,
        private val onItemLongClicked: (contact: Contact) -> Boolean,
    ) : RecyclerView.ViewHolder(view) {

        fun bind(contact: Contact) {
            val binding = ItemContactBinding.bind(view)
            with(binding) {
                fullNameTextView.text = "${contact.firstName} ${contact.lastName}"
                imageView.load(contact.imageUrl) {
                    placeholder(R.drawable.ic_placeholder)
                    error(R.drawable.ic_placeholder)
                    transformations(CircleCropTransformation())
                }
                root.setOnClickListener {
                    onItemClicked(contact)
                }
                root.setOnLongClickListener {
                    onItemLongClicked(contact)
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