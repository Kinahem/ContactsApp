package com.example.contactsapp.backendapi

import com.example.contactsapp.models.*
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface DjangoApi {
    @GET("/contacts/")
    suspend fun getContacts() : Response<List<Contact>>

    @POST("/api/token/")
    fun  getToken(
        @Body info: LogInBody
    ): Call<Token>

    @POST("/api/signup/")
    fun createAccount(
        @Body info: RegistrationBody
    ): Call<RegistrationResponse>
}