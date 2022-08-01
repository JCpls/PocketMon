package com.justin.pocketmon.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.Job


class HomeViewModel: ViewModel() {

    val articleData = MutableLiveData<List<Articledata>>()

    private val db = FirebaseFirestore.getInstance()

    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {

        getData()

    }

    fun getData() {
        val itemData = mutableListOf<Articledata>()
        db.collection("Article")
            .orderBy("createdTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Logger.i("document.data => ${document.data}")

                    val name = document.data["name"]
                    val category = document.data["category"]
                    val content = document.data["content"]
                    val createdTime = document.data["createdTime"]
                    val uid = document.data["uid"]
                    val title = document.data["title"]
                    val image = document.data["image"]
                    val id = document.data["id"]
                    val email = document.data["email"]

                    itemData.add(
                        Articledata(
                            category as String,
                            content as String,
                            createdTime as Timestamp,
                            title as String,
                            uid as String,
                            image as String,
                            email as String,
                            id as String,
                            name as String,
                        )
                    )
                }
                Logger.i("take a look at packed data => $itemData")

                articleData.value = itemData

            }
            .addOnFailureListener { exception ->

                Logger.i("nothing is packed")

            }
    }

    // Handle navigation to detail
    private val _navigateToDetail = MutableLiveData<Articledata>()

    val navigateToDetail: LiveData<Articledata>
        get() = _navigateToDetail

    fun navigateToDetail(articledata: Articledata) {
        _navigateToDetail.value = articledata
    }
    fun onDetailNavigated() {
        _navigateToDetail.value = null
    }

}