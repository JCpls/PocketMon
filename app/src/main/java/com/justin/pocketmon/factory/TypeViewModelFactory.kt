package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.home.HomeViewModel

@Suppress("UNCHECKED_CAST")
class TypeViewModelFactory
//    (
//    private val type: String,
//    private val repository: PocketmonRepository,
//) :
//    ViewModelProvider.Factory {
//
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
//            return HomeViewModel(type,repository) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
//}