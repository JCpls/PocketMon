package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*


interface PocketmonDataSource {

    suspend fun loginMockData(id: String): Result<Author>

    suspend fun getArticles(): Result<List<Plan>>

    suspend fun getBroadcasts(): Result<List<Broadcast>>

    suspend fun getToDoList(plan: Plan): Result<Plan>

    suspend fun getCommentList(): Result<List<ArticleData>>

    fun getLiveComments(articleId: String): MutableLiveData<List<Comment>>

    fun getLiveToDoList(userId: String, planId: String): MutableLiveData<Plan>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>>

    suspend fun addUser(user: User): Result<Boolean>

    suspend fun pushArticle(articledata: ArticleData): Result<Boolean>

    suspend fun publishPlan(plan: Plan): Result<Boolean>

    suspend fun publishBroadcast (broadcast: Broadcast): Result<Boolean>

    suspend fun addToDo(plan: Plan): Result<Boolean>

    suspend fun addCheckboxStatus(plan: Plan): Result<Boolean>

    suspend fun addComment(comment: Comment): Result<Boolean>

    suspend fun getGroupChatroom(ownerId: String): Result<Chatroom>

    suspend fun addChatroom(chatroom: Chatroom): Result<Boolean>

    suspend fun getAllChatroom(): Result<List<Chatroom>>

    suspend fun getChats(chatroomId: String): Result<List<Chat>>

    suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean>

    suspend fun addChatroomMessageAndTime(chatroomId: String, message: String): Result<Boolean>
}
