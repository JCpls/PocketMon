package com.justin.pocketmon.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.data.ToDo
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.plan.edit.PlanEditViewModel
import com.justin.pocketmon.plan.todo.PlanToDoViewModel

//@Suppress("UNCHECKED_CAST")
//class ToDoViewModelFactory constructor(
//    private val todo: ToDo,
//    private val repository: PocketmonRepository
//) : ViewModelProvider.NewInstanceFactory() {
//
//    override fun <T : ViewModel> create(modelClass: Class<T>) =
//        with(modelClass) {
//            when {
//
//                isAssignableFrom(PlanEditViewModel::class.java) ->
//                    PlanToDoViewModel(todo, repository)
//
//                else ->
//                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
//            }
//        } as T
//}