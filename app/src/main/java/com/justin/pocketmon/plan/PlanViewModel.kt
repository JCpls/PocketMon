package com.justin.pocketmon.plan

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.Result
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlanViewModel(private val repository: PocketmonRepository) : ViewModel() {

    // plan for getting liveData from firebase
    private val _plan = MutableLiveData<List<Plan>>()

    val plan: LiveData<List<Plan>>
        get() = _plan


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

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

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

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]${this}")
        Logger.i("------------------------------------")
        getArticlesResult()
//        publishPlan()
    }

//    fun publishPlan(plan: Plan) {
//        Log.i("justin","???????????????????????????plan1" )
//        coroutineScope.launch {
//            Log.i("justin","???????????????????????????plan2")
//            _status.value = LoadApiStatus.LOADING
//
//            when (val result = repository.publishPlan(plan)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    leave(true)
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }

    fun getArticlesResult() {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getArticles()

            _plan.value = when (result) {
                is Result.Success -> {
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    result.data
                }
                is Result.Fail -> {
                    _error.value = result.error
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                is Result.Error -> {
                    _error.value = result.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                    null
                }
                else -> {
                    _error.value = PocketmonApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                    null
                }
            }
            _refreshStatus.value = false
        }
    }

    fun refresh() {

        if (PocketmonApplication.instance.isLiveDataDesign()) {
            _status.value = LoadApiStatus.DONE
            _refreshStatus.value = false

        } else {
            if (status.value != LoadApiStatus.LOADING) {
//                getArticlesResult()
            }
        }
    }

    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun onLeft() {
        _leave.value = null
    }

    // Handle navigation to planEditFragment
    private val _navigateToPlanEdit = MutableLiveData<Plan>()

    val navigateToPlanEdit: LiveData<Plan>
        get() = _navigateToPlanEdit

    fun navigateToPlanEdit(plan: Plan) {
        _navigateToPlanEdit.value = plan
    }
    fun onPlanNavigated() {
        _navigateToPlanEdit.value = null
    }



}
