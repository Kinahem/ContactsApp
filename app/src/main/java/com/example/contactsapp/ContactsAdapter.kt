package com.example.contactsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactsapp.models.Contact
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactsAdapter (var contacts: ArrayList<Contact>): RecyclerView.Adapter<ContactsAdapter.PostViewHolder>() {

    fun updateContacts(newContacts: List<Contact>) {
        contacts.clear()
        contacts.addAll(newContacts)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_contact, parent, false)

        return PostViewHolder(view)
    }
    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(contacts[position])
    }
    class PostViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val contactFullName = view.contactFullName
        private val contactPhone = view.contactPhone
        private val contactEmail = view.contactEmail

        fun bind(contact: Contact) {
            contactFullName.text = "Name: " + contact.full_name
            contactPhone.text = "Phone: " + contact.phone.toString()
            contactEmail.text = "Email: " + contact.email
        }
    }
}