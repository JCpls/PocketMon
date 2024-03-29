package com.justin.pocketmon.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*
import com.justin.pocketmon.data.source.PocketmonDataSource

class PocketmonLocalDataSource(val context: Context) : PocketmonDataSource {


    override suspend fun getArticles(): Result<List<Plan>> {
        TODO("Not yet implemented")
    }

    override suspend fun getBroadcasts(): Result<List<Broadcast>> {
        TODO("Not yet implemented")
    }

    override suspend fun getToDoList(plan: Plan): Result<Plan> {
        TODO("Not yet implemented")
    }

    override suspend fun getCommentList(): Result<List<ArticleData>> {
        TODO("Not yet implemented")
    }

    override fun getLiveComments(
        articleId: String
    ): MutableLiveData<List<Comment>> {
        TODO("Not yet implemented")
    }

    override fun getLiveToDoList(userId: String, planId: String): MutableLiveData<Plan> {
        TODO("Not yet implemented")
    }

    override fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>> {
        TODO("Not yet implemented")
    }

    override suspend fun addUser(user: User): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun pushArticle(articledata: ArticleData): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun publishPlan(plan: Plan): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun publishBroadcast (broadcast: Broadcast): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addToDo(plan: Plan): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addCheckboxStatus(plan: Plan): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addComment(comment: Comment): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getGroupChatroom(groupId: String): Result<Chatroom> {
        TODO("Not yet implemented")
    }

    override suspend fun addChatroom(chatroom: Chatroom): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChatroom(): Result<List<Chatroom>> {
        TODO("Not yet implemented")
    }

    override suspend fun getChats(chatroomId: String): Result<List<Chat>> {
        TODO("Not yet implemented")
    }

    override suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addChatroomMessageAndTime(
        chatroomId: String,
        message: String
    ): Result<Boolean> {
        TODO("Not yet implemented")
    }

}
