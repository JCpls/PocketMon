package com.justin.pocketmon.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.justin.pocketmon.data.Articledata
import com.justin.pocketmon.data.source.PocketmonRepository

class DetailViewModel

    (private val articledata: Articledata, private val repository: PocketmonRepository) : ViewModel() {


    private val _selectedDream = MutableLiveData<Articledata>().apply {
        value = articledata
    }
    val selectedDream: LiveData<Articledata>
        get() = _selectedDream

//    init {
//        _selectedDream.value = articledata
//    }

}