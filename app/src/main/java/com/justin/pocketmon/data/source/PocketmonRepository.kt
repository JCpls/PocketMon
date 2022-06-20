package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.Article
import com.justin.pocketmon.data.Author
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.Result


/* Interface to the PocketMon layers.
*/
interface PocketmonRepository {

    suspend fun loginMockData(id: String): Result<Author>

    suspend fun getArticles(): Result<List<Plan>>

    fun getLiveArticles(): MutableLiveData<List<Article>>

    suspend fun publishPlan (plan: Plan): Result<Boolean>

    suspend fun delete(article: Article): Result<Boolean>
}

