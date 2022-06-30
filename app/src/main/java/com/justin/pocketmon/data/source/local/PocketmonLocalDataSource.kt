package com.justin.pocketmon.data.source.local

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.*
import com.justin.pocketmon.data.source.PocketmonDataSource

/*
* Concrete implementation of a PocketMon source as a db.
*/
class PocketmonLocalDataSource(val context: Context) : PocketmonDataSource {

//    override suspend fun login(id: String): Result<Author> {
//        return when (id) {
//            "waynechen323" -> Result.Success((Author(
//                id,
//                "AKA小安老師",
//                "wayne@school.appworks.tw"
//            )))
//            "dlwlrma" -> Result.Success((Author(
//                id,
//                "IU",
//                "dlwlrma@school.appworks.tw"
//            )))
//            //TODO add your profile here
//            else -> Result.Fail("You have to add $id info in local data source")
//        }
//    }

    override suspend fun loginMockData(id: String): Result<Author> {
        TODO("Not yet implemented")
    }

    override suspend fun getArticles(): Result<List<Plan>> {
        TODO("Not yet implemented")
    }


    override fun getLiveArticles(): MutableLiveData<List<Article>> {
        TODO("Not yet implemented")
    }

    override suspend fun publishPlan(plan: Plan): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun addToDo(plan: Plan): Result<Boolean> {
        TODO("Not yet implemented")
    }

    override suspend fun delete(article: Article): Result<Boolean> {
        TODO("Not yet implemented")
    }


}
