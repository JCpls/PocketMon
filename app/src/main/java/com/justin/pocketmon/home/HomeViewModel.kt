package com.justin.pocketmon.home

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.justin.pocketmon.data.Articledata
import kotlinx.coroutines.Job


class HomeViewModel : ViewModel() {

    val articleData = MutableLiveData<List<Articledata>>()

    private val db = FirebaseFirestore.getInstance()
    val document = db.collection("Article").document()


    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    init {
        Log.d("justin", "回到HomeFragment")
        getData()
//        getFakeData()

    }

    fun getData() {
        val itemData = mutableListOf<Articledata>()
        db.collection("Article")
            .orderBy("createdTime", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    Log.d("justin", "全部的data是 => ${document.data}")

                    val id = document.data["name"]
                    val category = document.data["category"]
                    val content = document.data["content"]
                    val createdTime = document.data["createdTime"]
                    val uid = document.data["uid"]
                    val title = document.data["title"]

                    itemData.add(
                        Articledata(
                            category as String,
                            content as String,
                            createdTime as Timestamp,
                            title as String,
                            uid as String,
                        )
                    )
// for---------------------------
                }
                Log.d("justin", "觀察一下做出來的的整個資料 => $itemData ")
                articleData.value = itemData

            }
            .addOnFailureListener { exception ->
                Log.d("justin", "沒東西 ")

            }


//fun-------------------------
    }


}