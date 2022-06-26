package com.justin.pocketmon.detail

import android.app.Application
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.data.Article
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.source.PocketmonRepository

class DetailViewModel

    (private val articledata: Articledata, private val repository: PocketmonRepository) : ViewModel() {


    private val _selectedDream = MutableLiveData<Articledata>().apply {
        value = articledata
    }
    val selectedDream: LiveData<Articledata>
        get() = _selectedDream

    // Handle leave detail
    private val _leaveDetail = MutableLiveData<Boolean>()

    val leaveDetail: LiveData<Boolean>
        get() = _leaveDetail

    // Handle navigation to PlanPage
    private val _navigateToPlanPage = MutableLiveData<Articledata>()

    val navigateToPlanPage: LiveData<Articledata>
        get() = _navigateToPlanPage

    fun navigateToStartPlan(articledata: Articledata) {
        _navigateToPlanPage.value = articledata
    }

    fun onDetailtoPlanPageNavigated() {
        _navigateToPlanPage.value = null
    }

    fun leaveDetail() {
        _leaveDetail.value = true
    }

//    init {
//        _selectedDream.value = articledata
//    }

}

// it for gallery circles design
//    private val _snapPosition = MutableLiveData<Int>()
//
//    val snapPosition: LiveData<Int>
//        get() = _snapPosition

/**
 * When the gallery scroll, at the same time circles design will switch.
 */
//    fun onGalleryScrollChange(
//        layoutManager: RecyclerView.LayoutManager?,
//        linearSnapHelper: LinearSnapHelper
//    ) {
//        val snapView = linearSnapHelper.findSnapView(layoutManager)
//        snapView?.let {
//            layoutManager?.getPosition(snapView)?.let {
//                if (it != snapPosition.value) {
//                    _snapPosition.value = it
//                }
//            }
//        }
//    }