package com.justin.pocketmon.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.User
import com.justin.pocketmon.util.Util.getString

object UserManager {

    private const val USER_DATA = "user_data"
    private const val USER_TOKEN = "user_token"
    private const val USER_ID = "user_Id"

//    private val _user = MutableLiveData<User>()
//
//    val user: LiveData<User>
//        get() = _user

//    var user = MutableLiveData<User?>()

    var user = User()

    var userId: String? = null
        get() = PocketmonApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_ID, null)

        set(value) {
            field = when (value) {
                null -> {
                    PocketmonApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_ID)
                        .apply()
                    null
                }
                else -> {
                    PocketmonApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_ID, value)
                        .apply()
                    value
                }
            }
        }

    var userToken: String? = null
        get() = PocketmonApplication.instance
            .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE)
            .getString(USER_TOKEN, null)

        set(value) {
            field = when (value) {
                null -> {
                    PocketmonApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .remove(USER_TOKEN)
                        .apply()
                    null
                }
                else -> {
                    PocketmonApplication.instance
                        .getSharedPreferences(USER_DATA, Context.MODE_PRIVATE).edit()
                        .putString(USER_TOKEN, value)
                        .apply()
                    value
                }
            }
        }

    /**
     * It can be use to check login status directly
     */
    val isLoggedIn: Boolean
        get() = userToken != null

    /**
     * Clear the [userToken] and the [user]/[_user] data
     */
    fun clear() {
        userToken = null
//        user.value = null
    }

    private var lastChallengeTime: Long = 0
    private var challengeCount: Int = 0
    private const val CHALLENGE_LIMIT = 23

    /**
     * Winter is coming
     */
    fun challenge() {
        if (System.currentTimeMillis() - lastChallengeTime > 5000) {
            lastChallengeTime = System.currentTimeMillis()
            challengeCount = 0
        } else {
            if (challengeCount == CHALLENGE_LIMIT) {
                userToken = null
                Toast.makeText(
                    PocketmonApplication.instance,
                    getString(R.string.profile_mystic_information),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                challengeCount++
            }
        }
    }
}
