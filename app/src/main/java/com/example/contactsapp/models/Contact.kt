package com.example.contactsapp.models

data class Contact(
    val id: Int,
    val full_name: String,
    val owner: String,
    val phone: Int?,
    val email: String?,
    val info: String?,
)