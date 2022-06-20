package com.justin.pocketmon.home.item

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.R
import com.justin.pocketmon.home.HomeTypeFilter
import javax.sql.DataSource

/* Factory for PagingDataSource
*/
//class PagingDataSourceFactory(val type: HomeTypeFilter) : DataSource.Factory<String, Product>() {
//
//    val sourceLiveData = MutableLiveData<PagingDataSource>()
//
//    override fun create(): DataSource<String, Product> {
//        val source = PagingDataSource(type)
//        sourceLiveData.postValue(source)
//        return source
//    }
//}
