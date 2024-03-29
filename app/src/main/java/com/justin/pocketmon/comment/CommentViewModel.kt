package com.justin.pocketmon.comment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.ArticleData
import com.justin.pocketmon.data.Comment
import com.justin.pocketmon.data.Result
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CommentViewModel (private val articledata: ArticleData, private val repository: PocketmonRepository) : ViewModel() {

    private val _addComment = MutableLiveData<ArticleData>().apply{
        value = articledata
    }
    val addComment: LiveData<ArticleData>
        get() = _addComment


    // Handle navigation to DetailPage
    private val _navigateToDetailPage = MutableLiveData<ArticleData?>()

    val navigateToDetailPage: LiveData<ArticleData?>
        get() = _navigateToDetailPage

    fun navigateToDetailPage() {
        _navigateToDetailPage.value = articledata
    }

    fun onCommentToDetailNavigated() {
        _navigateToDetailPage.value = null
    }

    private val _leave = MutableLiveData<Boolean?>()

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

    fun addComment(comment: Comment) {
        Log.i("justin","檢查addComment有無收到plan1" )
        coroutineScope.launch {
            Log.i("justin","檢查addComment有無收到plan2")
            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addComment(comment)) {
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

}