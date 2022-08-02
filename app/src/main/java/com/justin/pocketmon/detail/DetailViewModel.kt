package com.justin.pocketmon.detail

import androidx.lifecycle.*
import com.google.firebase.Timestamp
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.*
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class DetailViewModel

    (private val articledata: ArticleData, private val repository: PocketmonRepository) : ViewModel() {


    private val _selectedDream = MutableLiveData<ArticleData>().apply {
        value = articledata
    }
    val selectedDream: LiveData<ArticleData>
        get() = _selectedDream


    // for livedata observe
    private val _isLiveCommentListReady = MutableLiveData<Boolean>()

    val isLiveCommentListReady: LiveData<Boolean>
        get() = _isLiveCommentListReady

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
    private val _navigateToComment = MutableLiveData<ArticleData>()

    val navigateToComment: LiveData<ArticleData>
        get() = _navigateToComment

    fun navigateToCommentDialog() {
        _navigateToComment.value = articledata
    }

    fun onDetailtoCommentDialogNavigated() {
        _navigateToComment.value = null
    }

    fun navigateToComment() {
        _navigateToComment.value = articledata
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


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }


    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")

        getLiveComments(articledata.id)

    }

    fun publishPlan(plan: Plan) {
        Logger.i("check if planPage receives plan 1")
        coroutineScope.launch {
            Logger.i("check if planPage receives plan 2")
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
                    _error.value = PocketmonApplication.instance.getString(R.string.pls_try_again)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }


    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }


    fun timeStampToDate(createdTime: Timestamp): String {
        val date = SimpleDateFormat("MM/dd, yyyy", Locale.CHINESE).format(createdTime.toDate())
        Logger.i("date = $date")
        return date
    }

    // the liveData to get "getLiveComment" data from firebase
    var liveComment = MutableLiveData<List<Comment>>()

    private fun getLiveComments(articleId: String) {
        coroutineScope.launch {
            liveComment = repository.getLiveComments(articleId)
            Logger.i("getLiveComment() liveComent = $liveComment")
            Logger.i("getLiveComment() liveComment.value = ${liveComment.value}")
            _isLiveCommentListReady.value = true
        }
    }
}