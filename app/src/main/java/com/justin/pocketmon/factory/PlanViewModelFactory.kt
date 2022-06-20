package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.data.Plan
import com.justin.pocketmon.data.User
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.plan.PlanViewModel
import com.justin.pocketmon.plan.edit.PlanEditViewModel

/* Factory for all ViewModels which need [Author].
*/
@Suppress("UNCHECKED_CAST")
class UserViewModelFactory
//    (
//    private val repository: PocketmonRepository,
//) : ViewModelProvider.Factory

{

//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//
//        if (modelClass.isAssignableFrom(PlanEditViewModel::class.java)) {
//            return PlanEditViewModel(repository) as T
//        }
//
//        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//    }
}