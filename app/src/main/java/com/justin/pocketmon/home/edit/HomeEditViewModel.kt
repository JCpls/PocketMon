package com.justin.pocketmon.home.edit

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import com.justin.pocketmon.data.Articledata
import kotlinx.coroutines.Job


class HomeEditViewModel : ViewModel() {

    private var viewModelJob = Job()
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    val db = FirebaseFirestore.getInstance()


    fun addData(article: Articledata) {

        db.collection("Article").add(article).addOnSuccessListener {
            Log.d("justin", "成功 id =>${it}")
            Log.d("justin", "新增了 =>${article}")
        }
            .addOnFailureListener {
                Log.d("justin", "失敗,${it}")
            }

    }

//    fun checkAuthor(article: Articledata){
//        db.collection("Author")
//            .whereEqualTo("name", article.name)
//            .get()
//            .addOnSuccessListener { result ->
//                if (result.size() > 0) {
//                    Log.d("justin", "確實有這個作者")
//
//                    addData(article)
//                } else {
//                    Log.d("justin", "沒有這個作者的資訊")
//                }
//
//            }
//            .addOnFailureListener { exception ->
//                Log.d("justin", "錯誤")
//
//            }
//    }


}