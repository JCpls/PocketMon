package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*


/* Main entry point for accessing PocketMon sources.
*/
interface PocketmonDataSource {

    suspend fun loginMockData(id: String): Result<Author>

    suspend fun getArticles(): Result<List<Plan>>

    suspend fun getToDoList(plan: Plan): Result<Plan>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publishPlan(plan: Plan): Result<Boolean>

    suspend fun addToDo(plan: Plan): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>
}
