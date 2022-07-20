package com.justin.pocketmon.data.source.remote

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.*
import com.justin.pocketmon.data.source.PocketmonDataSource
import com.justin.pocketmon.login.UserManager
import com.justin.pocketmon.util.Logger
import java.util.*

import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/* Implementation of the PocketMon source that from network.
*/
object PocketmonRemoteDataSource : PocketmonDataSource {

    private const val PATH_PLANS = "Plans"
    private const val PATH_ARTICLE = "Article"
    private const val PATH_CHATROOM = "ChatRooms"
    private const val PATH_COMMENTS = "Comments"
    private const val PATH_BROADCAST = "Broadcasts"
    private const val KEY_CREATED_TIME = "createdTime"
    private const val PATH_CHATS = "chats"
    private const val  PATH_USERS = "Users"



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


    override suspend fun getBroadcasts(): Result<List<Broadcast>> = suspendCoroutine { continuation ->
        Logger.i("RemoteDataSource Broadcast check ")
        FirebaseFirestore.getInstance()
            .collection(PATH_BROADCAST)
            .orderBy("timeFinish", Query.Direction.DESCENDING)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val list = mutableListOf<Broadcast>()
                    for (document in task.result!!) {
                        Logger.d(document.id + " => " + document.data)

                        val broadcast = document.toObject(Broadcast::class.java)
                        list.add(broadcast)
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

    override fun getLiveToDoList(userId: String, planId: String): MutableLiveData<Plan> {
        val liveData = MutableLiveData<Plan>()
        Logger.i("getLiveToDoList userId = $userId")
        FirebaseFirestore.getInstance()
            .collection("Plans")
            .document(planId)
//            .whereEqualTo("ownerId", userId)
//            .orderBy("createdTime", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, exception ->
                Logger.i("getLiveToDoList addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                if (snapshot != null) {
//                            Logger.d(document.id + " => " + document.data)
//                            val plan = document.toObject(Plan::class.java)
                    Logger.i("snapshot.data = ${snapshot.data}")
                    val plan = snapshot.toObject(Plan::class.java)
                    Logger.i("snapshot plan = $plan")

                    plan?.let {
                        liveData.value = it
                    }

                } else {
                    Logger.w("[${this::class.simpleName}] getLiveToDoList snapshot == null")
                }
            }
        return liveData
    }




    override fun getLiveComments(articleId: String, commentId: String): MutableLiveData<Comment> {
        val liveData = MutableLiveData<Comment>()

        FirebaseFirestore.getInstance()
            .collection(PATH_COMMENTS)
            .document(articleId)
//            .whereEqualTo(FIELD_IMDB_ID, imdbID)
//            .orderBy(FIELD_CREATED_TIME, Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, exception ->
                Logger.i("getLiveComments addSnapshotListener detect")

                exception?.let {
                    Logger.w("[${this::class.simpleName}] Error getting documents. ${it.message}")
                }

                if (snapshot != null) {
//                    if (snapshot.size() >=1 ) {
//                        val list = mutableListOf<Comment>()
//                        snapshot.forEach { document ->
//                            Logger.d(document.id + " => " + document.data)
                    val comment = snapshot.toObject(Comment::class.java)
                    Logger.i("snapshot plan = $comment")

                    comment?.let {
                        liveData.value = it
                    }

                } else {
                    Logger.w("[${this::class.simpleName}] getLiveComment snapshot == null")
                }
            }
        return liveData
    }




    override fun getLiveArticles(): MutableLiveData<List<Article>> {

        val liveData = MutableLiveData<List<Article>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLE)
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


    override suspend fun pushArticle(articledata: Articledata): Result<Boolean> = suspendCoroutine { continuation ->
        val articleCollection = FirebaseFirestore.getInstance().collection("Article")
        val document = articleCollection.document()

        articledata.id = document.id

        document
            .set(articledata)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
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



    override suspend fun addCheckboxStatus (plan: Plan): Result<Boolean> = suspendCoroutine { continuation ->
        Logger.i("RemoteDataSource plan.degree = ${plan.degree}")
        Logger.i("RemoteDataSource plan.method = ${plan.method}")
        FirebaseFirestore.getInstance()
            .collection(PATH_PLANS)
            .document(plan.id)
            .set(plan)
//            .update(plan)
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


    override suspend fun publishBroadcast (broadcast: Broadcast): Result<Boolean> = suspendCoroutine { continuation ->
        Logger.i("RemoteDataSource broadcast.id = ${broadcast.id}")
        Logger.i("RemoteDataSource broadcast.title = ${broadcast.title}")
        FirebaseFirestore.getInstance()
            .collection(PATH_BROADCAST)
            .document(broadcast.id)
            .set(broadcast)
//            .update(broadcast)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("publish broadcast task.isSuccessful")
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


    override suspend fun addComment (articledata: Articledata): Result<Boolean> = suspendCoroutine { continuation ->
        Logger.i("RemoteDataSource articledata = $articledata")
        Logger.i("RemoteDataSource articledata.comment = ${articledata.comment}")
        Logger.i("articledata.uid = ${articledata.uid}")
        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLE)
            .document(articledata.uid)
            .update("comment", FieldValue.arrayUnion(articledata.comment.last()))
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("add comment task.isSuccessful")
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


    override suspend fun getCommentList(): Result<List<Articledata>> = suspendCoroutine { continuation ->

//        Logger.i("RemoteDataSource comment list articledata.uid = ${articledata.uid}")

        FirebaseFirestore.getInstance()
            .collection(PATH_ARTICLE)
            .orderBy(KEY_CREATED_TIME, Query.Direction.DESCENDING)
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

//                    val item = (task.result.toObject(Articledata::class.java)!!)
//                    Logger.i("task.result = ${item}")
//
//                    continuation.resume(Result.Success(item))
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
                    .collection(PATH_ARTICLE)
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

    override suspend fun addUser(user: User): Result<Boolean> = suspendCoroutine { continuation ->
        val groups = FirebaseFirestore.getInstance().collection(PATH_USERS)
        val document = groups.document(user.id)

        document
            .set(user)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.d("Add user=$user")
                    continuation.resume(Result.Success(true))
                } else {
                    task.exception?.let {
                        Logger.e("Error getting documents. ${it.message}")
                        continuation.resume(Result.Error(it))
                        return@addOnCompleteListener
                    }
                    continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                }
            }
    }



    override suspend fun getGroupChatroom(ownerId: String): Result<Chatroom> =
        suspendCoroutine { continuation ->
            Logger.d("getGroupChatroom start 2")
            Logger.d("UserManager.user.id= ${UserManager.user.id}")
            UserManager.user.id.let { id ->
                Logger.d("UserManager.user.id=$id")
                Logger.d("ownerId=$ownerId")
                FirebaseFirestore.getInstance()
                    .collection(PATH_CHATROOM)
//                    .whereEqualTo(KEY_GROUP_ID, groupId)
//                    .wherein(plan.id, senderId)
                    .whereArrayContainsAny("member", listOf(ownerId, id))
                    .get()
                    .addOnSuccessListener {
                        Logger.d("addOnSuccessListener")
                    }
                    .addOnFailureListener {
                        Logger.d("addOnFailureListener")
                    }
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            if (task.result.isEmpty) {
                                Logger.d("getGroupChatroom task.result.documents =${task.result.documents}")
                                continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                            } else {
                                for (document in task.result) {
                                    Logger.d("document=${document}")
                                    val chatroom = document.toObject(Chatroom::class.java)
                                    Logger.d("user=${chatroom}")
                                    continuation.resume(Result.Success(chatroom))
                                }
                            }
                        } else {
                            task.exception?.let {
                                Logger.e("Error getting documents. ${it.message}")
                                continuation.resume(Result.Error(it))
                                return@addOnCompleteListener
                            }
                            continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                        }
                    }
            }
        }
//
    override suspend fun addChatroom(chatroom: Chatroom): Result<Boolean> =
        suspendCoroutine { continuation ->
            val chatroomCollection = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val document = chatroomCollection.document()

            chatroom.id = document.id

            document
                .set(chatroom)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.d("Add chatroom=$chatroom")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Logger.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }
//
//
    override suspend fun getAllChatroom(): Result<List<Chatroom>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
//            .orderBy(KEY_LAST_TALK_TIME,Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val list = mutableListOf<Chatroom>()
                        for (document in task.result) {
                            Logger.d(document.id + " => " + document.data)

                            val chatroom = document.toObject(Chatroom::class.java)
                            list.add(chatroom)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            Logger.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }


    //--------- 不用使用 ----------
//    override suspend fun getTypeChatroom(type: String): Result<List<Chatroom>> =
//        suspendCoroutine { continuation ->
//            FirebaseFirestore.getInstance()
//                .collection(PATH_CHATROOM)
//                .whereEqualTo(KEY_TYPE, type)
////            .orderBy(KEY_LAST_TALK_TIME,Query.Direction.ASCENDING)
//                .get()
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//                        val list = mutableListOf<Chatroom>()
//                        for (document in task.result) {
//                            Timber.d(document.id + " => " + document.data)
//
//                            val chatroom = document.toObject(Chatroom::class.java)
//                            list.add(chatroom)
//                        }
//                        continuation.resume(Result.Success(list))
//                    } else {
//                        task.exception?.let {
//                            Timber.e("Error getting documents. ${it.message}")
//                            continuation.resume(Result.Error(it))
//                            return@addOnCompleteListener
//                        }
//                        continuation.resume(Result.Fail(MainApplication.instance.getString(R.string.you_know_nothing)))
//                    }
//                }
//        }
//
    override suspend fun getChats(chatroomId: String): Result<List<Chat>> =
        suspendCoroutine { continuation ->
            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(chatroomId)
                .collection(PATH_CHATS)
                .orderBy(KEY_CREATED_TIME, Query.Direction.ASCENDING)
                .get()
                .addOnCompleteListener { task ->
                    Logger.d("getChats addOnCompleteListener")
                    if (task.isSuccessful) {
                        val list = mutableListOf<Chat>()
                        for (document in task.result) {
                            Logger.d(document.id + " => " + document.data)

                            val chat = document.toObject(Chat::class.java)
                            list.add(chat)
                        }
                        continuation.resume(Result.Success(list))
                    } else {
                        task.exception?.let {
                            Logger.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }
//
    override suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean> =
        suspendCoroutine { continuation ->
            val chatroomCollection = FirebaseFirestore.getInstance().collection(PATH_CHATROOM)
            val chatroomDocument = chatroomCollection.document(chatroomId)
            val chatCollection = chatroomDocument.collection(PATH_CHATS)
            val chatDocument = chatCollection.document()

            chat.id = chatDocument.id
            chat.createdTime = Date(System.currentTimeMillis())

            chatDocument
                .set(chat)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.d("Add chat=$chat")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Logger.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }
//
    override suspend fun addChatroomMessageAndTime(
        chatroomId: String,
        message: String
    ): Result<Boolean> =
        suspendCoroutine { continuation ->

            FirebaseFirestore.getInstance()
                .collection(PATH_CHATROOM)
                .document(chatroomId)
                .update(
                    mapOf(
                        "lastTalkMessage" to message,
                        "lastTalkTime" to Date(System.currentTimeMillis())
                    )
                )
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Logger.d("Add chatroom message and time complete")
                        continuation.resume(Result.Success(true))
                    } else {
                        task.exception?.let {
                            Logger.e("Error getting documents. ${it.message}")
                            continuation.resume(Result.Error(it))
                            return@addOnCompleteListener
                        }
                        continuation.resume(Result.Fail(PocketmonApplication.instance.getString(R.string.you_know_nothing)))
                    }
                }
        }
//
    override fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>> {
        val liveData = MutableLiveData<List<Chat>>()

        FirebaseFirestore.getInstance()
            .collection(PATH_CHATROOM)
            .document(chatroomId)
            .collection(PATH_CHATS)
            .orderBy(KEY_CREATED_TIME, Query.Direction.ASCENDING)
            .addSnapshotListener { snapsot, exception ->
                Logger.i("addSnapshotListener detect")

                exception?.let {
                    Logger.w("Error getting documents. ${it.message}")
                }

                val list = mutableListOf<Chat>()
                for (document in snapsot!!) {
                    Logger.d(document.id + " => " + document.data)

                    val chat = document.toObject(Chat::class.java)
                    list.add(chat)
                }

                liveData.value = list
                Logger.d("liveData.value = ${liveData.value}")
            }
        return liveData
    }

}
