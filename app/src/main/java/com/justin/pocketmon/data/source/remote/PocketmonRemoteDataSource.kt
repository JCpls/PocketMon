package com.justin.pocketmon.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.*
import com.justin.pocketmon.data.source.PocketmonDataSource
import com.justin.pocketmon.util.Logger
import java.util.*

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/* Implementation of the PocketMon source that from network.
*/
object PocketmonRemoteDataSource : PocketmonDataSource {

    private const val PATH_PLANS = "Plans"
    private const val PATH_ARTICLES = "articles"
    private const val KEY_CREATED_TIME = "createdTime"

    override suspend fun loginMockData(id: String): Result<Author> {
        TODO("Not yet implemented")
    }


    override suspend fun getArticles(): Result<List<Plan>> = suspendCoroutine { continuation ->
        FirebaseFirestore.getInstance()
            .collection(PATH_PLANS)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Plan>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val plan = document.toObject(Plan::class.java)
                        list.add(plan)
                    }
                    continuation.resume(Result.Success(list))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }


    override suspend fun getToDoList(plan: Plan): Result<Plan> = suspendCoroutine { continuation ->

        Logger.i("RemoteDataSource todo belongs to plan.id = ${plan.id}")

        FirebaseFirestore.getInstance()
            .collection(PATH_PLANS)
//            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .document(plan.id)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    val list = mutableListOf<Plan>()
//                    for (document in task.result) {
//                        Logger.d(document.id + " => " + document.data)
//
//                        val todo = document.toObject(Plan::class.java)
//                        list.add(todo)
//                    }

                    val item = (task.result.toObject(Plan::class.java)!!)
                    Logger.i("task.result = ${item}")

                    continuation.resume(Result.Success(item))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }



    override fun getLiveArticles(): MutableLiveData<List<Article>> {

        val liveData = MutableLiveData<List<Article>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLES)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->

                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Article>()
                for (document in snapshot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val article = document.toObject(Article::class.java)
                    list.add(article)
                }

                liveData.value = list
            }
        return liveData
    }

    override suspend fun publishPlan (plan: Plan): Result<Boolean> = suspendCoroutine { continuation ->
        Log.i("justin","檢查計畫頁有無收到plan3")
        val plans = FirebaseFirestore.getInstance().collection(PATH_PLANS)
        Log.i("justin","檢查計畫頁有無收到plans = $plans")
        val document = plans.document()
        Log.i("justin","檢查計畫頁有無收到document = $document")

        plan.id = document.id
        Log.i("justin","檢查計畫頁有無收到plan = $plan")
        Log.i("justin","檢查計畫頁有無收到plan id ${plan.id}  => ${document.id}")

//      plan.createdTime = Calendar.getInstance().timeInMillis
        document
            .set(plan)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("Publish: $plan")

                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }

    override suspend fun addToDo (plan: Plan): Result<Boolean> = suspendCoroutine { continuation ->
        Logger.i("RemoteDataSource plan = $plan")
        Logger.i("RemoteDataSource plan.method = ${plan.method}")
        FirebaseFirestore.getInstance()
            .collection(PATH_PLANS)
            .document(plan.id)
            .update("method", FieldValue.arrayUnion(plan.method.last()))
//            .update("method", plan.method)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("add ToDo list task.isSuccessful")
                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {

                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }





    override suspend fun delete(article: Article): Result<Boolean> = suspendCoroutine { continuation ->

        when {
            article.author?.id == "waynechen323"
                    && article.tag.toLowerCase(Locale.TAIWAN) != "test"
                    && article.tag.trim().isNotEmpty() -> {

                continuation.resume(Result.Fail("You know nothing!! ${article.author.name}"))
            }
            else -> {
                FirebaseFirestore.getInstance()
                    .collection(PATH_ARTICLES)
                    .document(article.id)
                    .delete()
                    .addOnSuccessListener {
                        Logger.i("Delete: $article")

                        continuation.resume(Result.Success(true))
                    }.addOnFailureListener {
                        Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                    }
            }
        }

    }

}
