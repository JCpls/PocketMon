package com.justin.pocketmon.data.source

import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*

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

    override suspend fun getToDoList(plan:Plan): Result<Plan> {
        return remoteDataSource.getToDoList(plan)
    }

    override suspend fun getCommentList(): Result<List<Articledata>> {
        return remoteDataSource.getCommentList()
    }

    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        return remoteDataSource.getLiveArticles()
    }

    override suspend fun publishPlan (plan: Plan): Result<Boolean> {
        return remoteDataSource.publishPlan(plan)
    }

    override suspend fun addToDo (plan: Plan): Result<Boolean> {
        return remoteDataSource.addToDo(plan)
    }

    override suspend fun addCheckboxStatus (plan: Plan): Result<Boolean> {
        return remoteDataSource.addCheckboxStatus(plan)
    }

    override suspend fun addComment (articledata: Articledata): Result<Boolean> {
        return remoteDataSource.addComment(articledata)
    }


    override suspend fun delete(article: Article): Result<Boolean> {
        return remoteDataSource.delete(article)
    }
}