package com.justin.pocketmon.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.data.Articledata

class DetailViewModel

    (articledata: Articledata, app: Application) : AndroidViewModel(app) {

    private val _selectedProduct = MutableLiveData<Articledata>()
    val selectedProduct: LiveData<Articledata>
        get() = _selectedProduct

    init {
        _selectedProduct.value = articledata
    }

}