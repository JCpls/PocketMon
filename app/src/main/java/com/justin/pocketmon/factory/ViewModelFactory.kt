package com.justin.pocketmon.factory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.justin.pocketmon.MainViewModel
import com.justin.pocketmon.R
import com.justin.pocketmon.chat.ChatViewModel
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.home.HomeViewModel
import com.justin.pocketmon.home.edit.HomeEditViewModel
import com.justin.pocketmon.login.LoginViewModel
import com.justin.pocketmon.plan.PlanViewModel
import com.justin.pocketmon.plan.edit.PlanEditViewModel
import com.justin.pocketmon.profile.ProfileViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(
    private val repository: PocketmonRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(MainViewModel::class.java) ->
                    MainViewModel(repository)

                isAssignableFrom(HomeViewModel::class.java) ->
                    HomeViewModel()

                isAssignableFrom(PlanViewModel::class.java) ->
                    PlanViewModel(repository)

                isAssignableFrom(LoginViewModel::class.java) ->
                    LoginViewModel(repository)

                isAssignableFrom(ProfileViewModel::class.java) ->
                    ProfileViewModel(repository)

                isAssignableFrom(ChatViewModel::class.java) ->
                    ChatViewModel(repository)

                isAssignableFrom(HomeEditViewModel::class.java) ->
                    HomeEditViewModel(repository)

                else ->
                    throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
            }
        } as T
}
