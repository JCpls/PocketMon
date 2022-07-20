package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*


/* Interface to the PocketMon layers.
*/
interface PocketmonRepository {

    suspend fun loginMockData(id: String): Result<Author>

    suspend fun getArticles(): Result<List<Plan>>

    suspend fun pushArticle(articledata: Articledata): Result<Boolean>

    suspend fun getBroadcasts(): Result<List<Broadcast>>

    suspend fun getToDoList(plan: Plan): Result<Plan>

    suspend fun getCommentList(): Result<List<Articledata>>

    fun getLiveComments(articleId: String, commentId: String): MutableLiveData<Comment>

    fun getLiveToDoList(userId: String, planId: String): MutableLiveData<Plan>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>>

    suspend fun addUser(user: User): Result<Boolean>

    suspend fun publishPlan (plan: Plan): Result<Boolean>

    suspend fun publishBroadcast (broadcast: Broadcast): Result<Boolean>

    suspend fun addToDo (plan: Plan): Result<Boolean>

    suspend fun addCheckboxStatus (plan: Plan): Result<Boolean>

    suspend fun addComment (articledata: Articledata): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>

    suspend fun getGroupChatroom(groupId: String): Result<Chatroom>

    suspend fun addChatroom(chatroom: Chatroom): Result<Boolean>

    suspend fun getAllChatroom(): Result<List<Chatroom>>

    suspend fun getChats(chatroomId: String): Result<List<Chat>>

    suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean>

    suspend fun addChatroomMessageAndTime(chatroomId: String, message: String): Result<Boolean>

}

