package com.example.contactsapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.contactsapp.views.ContactsListView
import com.example.contactsapp.ContactsAdapter
import kotlinx.android.synthetic.main.activity_contacts.*

class ContactsActivity : AppCompatActivity() {
    lateinit var viewModel: ContactsListView
    private var contactsAdapter = ContactsAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contacts)
        viewModel = ViewModelProvider(this).get(ContactsListView::class.java)
        viewModel.refresh()
        Log.d("ayush: ", viewModel.posts.value.toString())

//        postsListAdapter.setOnItemClickListener(object : ContactsAdapter.onItemClickListener{
//            override fun onItemClick(position: Int) {
//                val intent = Intent(this@ContactsActivity, PostActivity::class.java)
//                intent.putExtra("post_id", viewModel.posts.value?.get(position)?.id)
//                intent.putExtra("post_title", viewModel.posts.value?.get(position)?.title)
//                intent.putExtra("post_owner", viewModel.posts.value?.get(position)?.owner)
//                intent.putExtra("post_content", viewModel.posts.value?.get(position)?.content)
//                startActivity(intent)
//                Log.d("ayush: ", "${position}")
//            }
//        })

        postsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = contactsAdapter
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.posts.observe(this, Observer { posts ->
            posts?.let {
                postsList.visibility = View.VISIBLE
                contactsAdapter.updateContacts(it)
            }
        })

        viewModel.postsLoadError.observe(this, Observer { isError ->
            listError.visibility = if(isError == "") View.GONE else View.VISIBLE
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            isLoading?.let {
                loadingView.visibility = if (it) View.VISIBLE else View.GONE
                if (it) {
                    listError.visibility = View.GONE
                    postsList.visibility = View.GONE
                }
            }
        })
    }
}