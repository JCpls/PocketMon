package com.justin.pocketmon.profile

import androidx.lifecycle.ViewModel
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.login.UserManager

class ProfileViewModel(val repository: PocketmonRepository) : ViewModel() {
    // get user id from UserManager
    val user = UserManager.user

}