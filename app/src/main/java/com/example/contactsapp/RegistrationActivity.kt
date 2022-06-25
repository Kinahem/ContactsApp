package com.example.contactsapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.contactsapp.backendapi.DjangoApi
import com.example.contactsapp.backendapi.RetrofitHelper
import com.example.contactsapp.models.RegistrationBody
import com.example.contactsapp.models.RegistrationResponse
import kotlinx.android.synthetic.main.activity_registration.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationActivity : AppCompatActivity() {
    var api = RetrofitHelper.getInstance().create(DjangoApi::class.java)
    val regResponse = MutableLiveData<RegistrationResponse>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        activity_main_regButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val fullname = fullnameEditText.text.toString().trim()
            val gender = genderEditText.text.toString().trim()
            val genderChar = when (gender.lowercase()) {
                "male" -> "M"
                "female" -> "F"
                else -> "O"
            }
            val date = dateEditText.text.toString().trim()
            val regInfo = RegistrationBody(email, username, password, fullname, date, genderChar)
            api.createAccount(regInfo).enqueue(object: Callback<RegistrationResponse> {
                override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                }

                override fun onResponse(call: Call<RegistrationResponse>, response: Response<RegistrationResponse>) {
                    regResponse.value = response.body()
                    Log.d("ayush: ", regResponse.value.toString())
                }
            })
            if (regResponse.value != null) {
                Toast.makeText(applicationContext,"Account created.", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(applicationContext,"Wrong Email or Password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}