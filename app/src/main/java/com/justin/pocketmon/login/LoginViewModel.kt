package com.justin.pocketmon.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.justin.pocketmon.PocketmonApplication
import com.justin.pocketmon.R
import com.justin.pocketmon.data.User
import com.justin.pocketmon.data.Result
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: PocketmonRepository) : ViewModel() {

    val user = User()

    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<User>()

    // Handle leave login
    private val _leaveLogin = MutableLiveData<Boolean>()

    val leaveLogin: LiveData<Boolean>
        get() = _leaveLogin

    // status: The internal MutableLiveData that stores the status of the most recent request
    private val _status = MutableLiveData<LoadApiStatus>()

    val status: LiveData<LoadApiStatus>
        get() = _status

    // error: The internal MutableLiveData that stores the error of the most recent request
    private val _error = MutableLiveData<String>()

    val error: LiveData<String>
        get() = _error

    // Create a Coroutine scope using a job to be able to cancel when needed
    private var viewModelJob = Job()

    // the Coroutine runs using the Main (UI) dispatcher
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    // google log in
    private lateinit var googleSignInAccount: GoogleSignInAccount
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    init {
        Logger.i("------------------------------------")
        Logger.i("[${this::class.simpleName}]$this")
        Logger.i("------------------------------------")
    }


    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        Logger.i("handleSignInResult()")
        try {
            googleSignInAccount = completedTask.getResult(ApiException::class.java)
            val googleId = googleSignInAccount.id ?: ""
            Logger.i("Google ID = $googleId")
            val googleFirstName = googleSignInAccount.givenName ?: ""
            Logger.i("Google First Name = $googleFirstName")
            val googleLastName = googleSignInAccount.familyName ?: ""
            Logger.i("Google Last Name = $googleLastName")
            val googleEmail = googleSignInAccount.email ?: ""
            Logger.i("Google Email = $googleEmail")
            val googleProfilePicURL = googleSignInAccount.photoUrl.toString()
            Logger.i("Google Profile Pic URL = $googleProfilePicURL")
            val googleIdToken = googleSignInAccount.idToken ?: ""
            Logger.i("Google ID Token = $googleIdToken")
            user.userToken = googleIdToken

            // UserManager.userToken got value
            UserManager.userToken = googleIdToken
            Logger.i("UserManager.userToken = ${UserManager.userToken}")
            val googleIsExpired = googleSignInAccount.isExpired
            Logger.i("Google isExpired = $googleIsExpired")

            user.name = googleSignInAccount.givenName + " " + googleSignInAccount.familyName
            Logger.i("user.name = ${user.name}")
            user.email = googleSignInAccount.email.toString()
            Logger.i("user.email = ${user.email}")
            user.image = googleSignInAccount.photoUrl.toString()
            Logger.i("user.pictureUri = ${user.image}")
            user.id = googleSignInAccount.id.toString()
            Logger.i("user.id = ${user.id}")


            UserManager.user = user
            addUserResult()


        } catch (e: ApiException) {

            Logger.e("Google log in failed = ${e.statusCode}")
        }

    }

    fun leaveLogin() {
        Logger.i("leave check2 ")
        _leaveLogin.value = true
    }

    fun addUserResult() {
        coroutineScope.launch {
            _status.value = LoadApiStatus.LOADING
            Logger.i("check for User data 01")
            when (val addUserResult = repository.addUser(user)) {
                is Result.Success -> {
                    Logger.i("check for User data 02")
                    _error.value = null
                    _status.value = LoadApiStatus.DONE
                    _leaveLogin.value = true
                }
                is Result.Fail -> {
                    _error.value = addUserResult.error
                    _status.value = LoadApiStatus.ERROR
                }
                is Result.Error -> {
                    _error.value = addUserResult.exception.toString()
                    _status.value = LoadApiStatus.ERROR
                }
                else -> {
                    _error.value =
                        PocketmonApplication.instance.getString(R.string.you_know_nothing)
                    _status.value = LoadApiStatus.ERROR
                }
            }
        }
    }
}
