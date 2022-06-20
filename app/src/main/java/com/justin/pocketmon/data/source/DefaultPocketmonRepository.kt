package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.Article
import com.justin.pocketmon.data.Author
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.Result

/* Concrete implementation to load PocketMon sources.
*/
class DefaultPocketmonRepository(private val remoteDataSource: PocketmonDataSource,
                                 private val localDataSource: PocketmonDataSource
) : PocketmonRepository {

    override suspend fun loginMockData(id: String): Result<Author> {
        return localDataSource.loginMockData(id)
    }

    override suspend fun getArticles(): Result<List<Plan>> {
        return remoteDataSource.getArticles()
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        return remoteDataSource.getLiveArticles()
    }

    override suspend fun publishPlan (plan: Plan): Result<Boolean> {
        return remoteDataSource.publishPlan(plan)
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        return remoteDataSource.delete(article)
    }
}