package com.justin.pocketmon.plan.todo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.Result
import com.justin.pocketmon.data.ToDo
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PlanToDoViewModel
    (private val plan: Plan, private val repository: PocketmonRepository) : ViewModel() {

        private val _addedTodo = MutableLiveData<Plan>().apply{
            value = plan
        }
        val addedTodo: LiveData<Plan>
        get() = _addedTodo


        // Handle navigation to PlanEditPage
        private val _navigateToPlanEditPage = MutableLiveData<Plan?>()

        val navigateToPlanEditPage: LiveData<Plan?>
            get() = _navigateToPlanEditPage

        fun onToDotoPlanEditNavigated() {
            _navigateToPlanEditPage.value = null
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
        }


        fun addToDo(plan: Plan) {
            coroutineScope.launch {
                _status.value = LoadApiStatus.LOADING

                when (val result = repository.addToDo(plan)) {
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

}






















