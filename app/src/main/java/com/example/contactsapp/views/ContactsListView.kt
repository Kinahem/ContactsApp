package com.example.contactsapp.views

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.contactsapp.backendapi.DjangoApi
import com.example.contactsapp.backendapi.RetrofitHelper
import com.example.contactsapp.models.Contact
import kotlinx.coroutines.*

class ContactsListView : ViewModel() {
    var api = RetrofitHelper.getInstance().create(DjangoApi::class.java)
    var job: Job? = null
    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    val posts = MutableLiveData<List<Contact>>()
    val postsLoadError = MutableLiveData<String?>()
    val loading = MutableLiveData<Boolean>()

    fun refresh() {
        fetchContacts()
    }

    private fun fetchContacts() {
        loading.value = true
        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            val response = api.getContacts()
            Log.d("contacts: ", response.body().toString())
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    posts.value = response.body()
                    postsLoadError.value = null
                    loading.value = false
                } else {
                    onError("Error : ${response.message()}")
                }
            }
        }
        postsLoadError.value = ""
        loading.value = false
    }

    private fun onError(s: String) {
        postsLoadError.value = s
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}