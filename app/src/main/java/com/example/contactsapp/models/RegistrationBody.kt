package com.example.contactsapp.models

data class RegistrationBody(
    val email: String,
    val username: String,
    val password: String,
    val full_name: String,
    val birth_date: String,
    val gender: String,
)
