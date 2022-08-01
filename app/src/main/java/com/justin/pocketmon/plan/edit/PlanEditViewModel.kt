package com.justin.pocketmon.plan.edit

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.findNavController
import com.justin.pocketmon.NavigationDirections
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

class PlanEditViewModel

    (private val plan: Plan, private val repository: PocketmonRepository) : ViewModel() {

    private val _selectedPlan = MutableLiveData<Plan>().apply {
        value = plan
    }

    val selectedPlan: LiveData<Plan>
        get() = _selectedPlan

    // for editting the degree from Plans
    val newDegree = MutableLiveData<Long>()


    // for livedata observe
    private val _isLiveToDoListReady = MutableLiveData<Boolean>()

    val isLiveToDoListReady: LiveData<Boolean>
        get() = _isLiveToDoListReady


    // the liveData to get "getToDoResult" data from firebase
    private val _planEdit = MutableLiveData<Plan>()

    // Handle leave planEdit
    private val _leavePlanEdit = MutableLiveData<Boolean>()

    val leavePlanEdit: LiveData<Boolean>
        get() = _leavePlanEdit

    // Handle navigate to PlanTodo dialog
    private val _navigateToPlanTodo = MutableLiveData<Plan>()

    val navigateToPlanTodo: LiveData<Plan>
        get() = _navigateToPlanTodo

    fun navigateToPlanTodo(plan: Plan) {
        _navigateToPlanTodo.value = plan
    }

    fun onPlanEditToPlanTodoNavigated() {
        _navigateToPlanTodo.value = null
    }

    fun navigateToAddTodo() {
        _navigateToPlanTodo.value = plan
    }


    private val _leave = MutableLiveData<Boolean>()

    val leave: LiveData<Boolean>
        get() = _leave

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // status for the loading icon of swl
    private val _refreshStatus = MutableLiveData<Boolean>()

    val refreshStatus: LiveData<Boolean>
        get() = _refreshStatus

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

        getLiveToDoList(plan.ownerId, plan.id )

    }


    // pass the value from degree to newDegree for editing its value with plus and minus calculation
    fun getDegree(){
        newDegree.value = liveToDoList.value?.degree
    }


    // the liveData to get "getLiveToDoList" data from firebase
    var liveToDoList = MutableLiveData<Plan>()

    private fun getLiveToDoList(userId: String, planId: String) {
        coroutineScope.launch {
            liveToDoList = repository.getLiveToDoList(userId, planId)
            Logger.i("getLiveTodoResult() liveToDo = $liveToDoList")
            Logger.i("getLiveTodoResult() liveToDo.value = ${liveToDoList.value}")
            _isLiveToDoListReady.value = true
        }
    }


    fun addCheckboxStatus(plan: Plan) {
        Logger.i("1st check if receive plan")
        coroutineScope.launch {
        Logger.i("2nd check if receive plan")
            _status.value = LoadApiStatus.LOADING

            when (val result = repository.addCheckboxStatus(plan)) {
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


    fun publishToBroadcast(broadcast: Broadcast) {
        Logger.i("1st check if receive plan")
        coroutineScope.launch {
        Logger.i("2nd check if receive plan")
            _status.value = LoadApiStatus.LOADING

            when (val result = repository.publishBroadcast(broadcast)) {
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

    fun leavePlanEdit() {
        _leavePlanEdit.value = true
    }


    fun DoneIsTrue(value:Boolean, todo: String){

        liveToDoList.value?.method

        for (value in liveToDoList.value?.method!!) {
            if (value.todo == todo) {

                Logger.i("into value of DoneIsTrue? = ${value}")

                value.done = true

                Logger.i("into CHANGED value of DoneIsTrue? = ${value}")

                updateData()
            }
        }

        Logger.i("true is activated? = ${newDegree.value}")
    }

    fun DoneIsFalse(value:Boolean, todo:String){

        liveToDoList.value?.method

        for (value in liveToDoList.value?.method!!) {
            if (value.todo == todo) {

                Logger.i("into value of DoneIsFalse? = ${value}")

                value.done = false

                Logger.i("into CHANGED value of DoneIsFalse? = ${value}")

                updateData()

            }
        }

        Logger.i("false is activated? = ${newDegree.value}")
    }


    // plus score of box checked into Dream degree
    fun addPoint(value:String){

        val score = value.toLong()
        newDegree.value = newDegree.value?.plus(score)
        Logger.i("add is activated?.plus(value.toLong()) = ${newDegree.value}")

    }

    // minus score of box checked into Dream degree
    fun minusPoint(value:String){

        val score = value.toLong()
        newDegree.value = newDegree.value?.minus(score)
        Logger.i("minus is activated?.minus(value.toLong()) = ${newDegree.value}")

    }

    fun updateData(){
        Logger.i("liveToDoList.value looks like = ${liveToDoList.value}")

        liveToDoList.value?.degree = newDegree.value!!

        val plan = liveToDoList.value!!

        addCheckboxStatus(plan)

        Logger.i("new liveToDoList.value looks like = ${liveToDoList.value}")
    }

}
