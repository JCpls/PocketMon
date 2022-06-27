package com.justin.pocketmon.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Article
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.Result
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

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


    // Handle leave to ChatRoom
    private val _navigateToChat = MutableLiveData<Boolean>()

    val navigateToChat: LiveData<Boolean>
        get() = _navigateToChat

    fun navigateToChatRoom() {
        _navigateToChat.value = true
    }

    fun onDetailtoChatRoomNavigated() {
        _navigateToChat.value = null
    }

    fun navigateToChat() {
        _navigateToChat.value = true
    }

    // Handle leave to comment
    private val _navigateToComment = MutableLiveData<Boolean>()

    val navigateToComment: LiveData<Boolean>
        get() = _navigateToComment

    fun navigateToCommentDialog() {
        _navigateToComment.value = true
    }

    fun onDetailtoCommentDialogNavigated() {
        _navigateToComment.value = null
    }

    fun navigateToComment() {
        _navigateToComment.value = true
    }


    // Handle navigation to PlanPage
    private val _navigateToPlanPage = MutableLiveData<Boolean?>()

    val navigateToPlanPage: LiveData<Boolean?>
        get() = _navigateToPlanPage

    fun navigateToStartPlan() {
        _navigateToPlanPage.value = true
    }

    fun onDetailtoPlanPageNavigated() {
        _navigateToPlanPage.value = null
    }

    fun leaveDetail() {
        _leaveDetail.value = true
    }

    private val _leave = MutableLiveData<Boolean?>()

    val leave: LiveData<Boolean?>
        get() = _leave


    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String?>()

    val error: LiveData<String?>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    /**
     * When the [ViewModel] is finished, we cancel our coroutine [viewModelJob], which tells the
     * Retrofit service to stop.
     */
    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


//    init {
//        _selectedDream.value = articledata
//    }


    fun publishPlan(plan: Plan) {
        Log.i("justin","檢查計畫頁有無收到plan1" )
        coroutineScope.launch {
            Log.i("justin","檢查計畫頁有無收到plan2")
            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publishPlan(plan)) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    leave(true)
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }


    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }


}


// ------ for detail gallery further upgrades

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