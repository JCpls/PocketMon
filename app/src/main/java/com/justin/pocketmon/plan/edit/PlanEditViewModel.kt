package com.justin.pocketmon.plan.edit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.Result
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.PlanMethod
import com.justin.pocketmon.data.User
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

    //
    val newDegree = MutableLiveData<Long>()


    // the liveData to get "getToDoResult" data from firebase
    private val _planEdit = MutableLiveData<Plan>()

    val planEdit: LiveData<Plan>
        get() = _planEdit


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

        getToDoResult(plan)


    }

    // 宣告 把前面拿到的degree 轉成 newDegree 才能對它進行加工(+,-)
    fun getDegree(){
        newDegree.value = planEdit.value?.degree
    }



    fun getToDoResult(plan: Plan) {

        coroutineScope.launch {

            _status.value = LoadApiStatus.LOADING

            val result = repository.getToDoList(plan)

            _planEdit?.value = when (result) {
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



    // when checkbox is selected and
//    fun removeProduct(product: Product) {
//        coroutineScope.launch {
//            PocketmonRepository.removeProductInCart(product.id, product.selectedVariant.colorCode, product.selectedVariant.size)
//        }
//    }

    //
    fun addCheckboxStatus(plan: Plan) {
        Log.i("justin","檢查ToDo有無收到plan1" )
        coroutineScope.launch {
            Log.i("justin","檢查ToDo有無收到plan2")
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


    fun leave(needRefresh: Boolean = false) {
        _leave.value = needRefresh
    }

    fun leavePlanEdit() {
        _leavePlanEdit.value = true
    }

    fun onLeft() {
        _leave.value = null
    }

    fun DoneIsTrue(value:Boolean, todo: String){

        _planEdit.value?.method

        for (value in _planEdit.value?.method!!) {
            if (value.todo == todo) {

                Logger.i("進到DoneIsTrue的value? = ${value}")
                value.done = true
                Logger.i("進到DoneIsTrue的改變後的value? = ${value}")
                updateData()
            }
        }

        Logger.i("true 有無啟動? = ${newDegree.value}")
    }

    fun DoneIsFalse(value:Boolean, todo:String){

        _planEdit.value?.method

        for (value in _planEdit.value?.method!!) {
            if (value.todo == todo) {
                Logger.i("進到DoneIsFalsee的value? = ${value}")
                value.done = false
                Logger.i("進到DoneIsFalse改變後的value? = ${value}")

                updateData()

            }
        }

        Logger.i("false 有無啟動? = ${newDegree.value}")
    }


    // plus score of box checked into Dream degree
    fun addPoint(value:String){
//      newDegree.value?.toLong()?.plus(value.toLong())
        val score = value.toLong()
        newDegree.value = newDegree.value?.plus(score)
//      newDegree.value = newDegree.value
        Logger.i("add 有無啟動?.plus(value.toLong()) = ${newDegree.value}")
    }

    // minus score of box checked into Dream degree
    fun minusPoint(value:String){
//        newDegree.value?.toLong()?.plus(value.toLong())
        val score = value.toLong()
        newDegree.value = newDegree.value?.minus(score)
//      newDegree.value = newDegree.value
        Logger.i("minus 有無啟動?.minus(value.toLong()) = ${newDegree.value}")
    }

    fun updateData(){
        Logger.i("確認東西到底長怎樣 = ${planEdit.value}")

        planEdit.value?.degree = newDegree.value!!


        val plan = planEdit.value!!

        addCheckboxStatus(plan)
        Logger.i("看一下要傳上去的東西長怎樣 = ${planEdit.value}")
    }

    fun dreamCompleted(){

    }

}
