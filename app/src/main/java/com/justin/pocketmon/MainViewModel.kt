package com.justin.pocketmon

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.util.CurrentFragmentType
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

/*
* The [ViewModel] that is attached to the [MainActivity].
*/
class MainViewModel(private val repository: PocketmonRepository) : ViewModel() {

//    private val _author = MutableLiveData<Plan>().apply {
//        value = Plan(
//            "justinyang29",
//            "Justin",
//            "瘦身計畫",
//            "哈哈哈",
//            2
//        )
//    }

    private val _refresh = MutableLiveData<Boolean>()

    val refresh: LiveData<Boolean>
        get() = _refresh

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // Record current fragment to support data binding
    val currentFragmentType = MutableLiveData<CurrentFragmentType>()

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
    }

    fun refresh() {
        if (!PocketmonApplication.instance.isLiveDataDesign()) {
            _refresh.value = true
        }
    }

    fun onRefreshed() {
        if (!PocketmonApplication.instance.isLiveDataDesign()) {
            _refresh.value = null
        }
    }
}
