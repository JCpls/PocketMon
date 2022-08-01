package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*


class DefaultPocketmonRepository(private val remoteDataSource: PocketmonDataSource,
                                 private val localDataSource: PocketmonDataSource
) : PocketmonRepository {

    override suspend fun loginMockData(id: String): Result<Author> {
        return localDataSource.loginMockData(id)
    }

    override suspend fun getArticles(): Result<List<Plan>> {
        return remoteDataSource.getArticles()
    }

    override suspend fun getBroadcasts(): Result<List<Broadcast>> {
        return remoteDataSource.getBroadcasts()
    }

    override fun getLiveToDoList(userId: String, planId: String): MutableLiveData<Plan>{
        return remoteDataSource.getLiveToDoList(userId, planId)
    }

    override fun getLiveComments(articleId: String): MutableLiveData<List<Comment>>{
        return remoteDataSource.getLiveComments(articleId)
    }

    override suspend fun getToDoList(plan:Plan): Result<Plan> {
        return remoteDataSource.getToDoList(plan)
    }

    override suspend fun getCommentList(): Result<List<ArticleData>> {
        return remoteDataSource.getCommentList()
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        return remoteDataSource.getLiveArticles()
    }

    override suspend fun pushArticle(articledata: ArticleData): Result<Boolean>{
        return remoteDataSource.pushArticle(articledata)
    }

    override suspend fun publishPlan (plan: Plan): Result<Boolean> {
        return remoteDataSource.publishPlan(plan)
    }

    override suspend fun publishBroadcast (broadcast: Broadcast): Result<Boolean> {
        return remoteDataSource.publishBroadcast(broadcast)
    }

    override suspend fun addToDo (plan: Plan): Result<Boolean> {
        return remoteDataSource.addToDo(plan)
    }

    override suspend fun addCheckboxStatus (plan: Plan): Result<Boolean> {
        return remoteDataSource.addCheckboxStatus(plan)
    }

    override suspend fun addComment (comment: Comment): Result<Boolean> {
        return remoteDataSource.addComment(comment)
    }

    override suspend fun addUser(user: User): Result<Boolean> {
        return remoteDataSource.addUser(user)
    }

    override suspend fun getGroupChatroom(groupId: String): Result<Chatroom>{
        return remoteDataSource.getGroupChatroom(groupId)
    }

    override suspend fun addChatroom(chatroom: Chatroom): Result<Boolean>{
        return remoteDataSource.addChatroom(chatroom)
    }

    override suspend fun getAllChatroom(): Result<List<Chatroom>>{
        return remoteDataSource.getAllChatroom()
    }

    override suspend fun getChats(chatroomId: String): Result<List<Chat>>{
        return remoteDataSource.getChats(chatroomId)
    }

    override suspend fun sendChat(chatroomId: String, chat: Chat): Result<Boolean> {
       return remoteDataSource.sendChat(chatroomId, chat)
    }

    override suspend fun addChatroomMessageAndTime(chatroomId: String, message: String): Result<Boolean>{
        return remoteDataSource.addChatroomMessageAndTime(chatroomId, message)
    }

    override fun getLiveChats(chatroomId: String): MutableLiveData<List<Chat>>{
        return remoteDataSource.getLiveChats(chatroomId)
    }
}