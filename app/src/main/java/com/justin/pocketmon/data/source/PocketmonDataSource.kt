package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.Article
import com.justin.pocketmon.data.Author
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.Result


/* Main entry point for accessing PocketMon sources.
*/
interface PocketmonDataSource {

    suspend fun loginMockData(id: String): Result<Author>

    suspend fun getArticles(): Result<List<Plan>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publishPlan(plan: Plan): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>
}
