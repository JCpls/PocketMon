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
import com.justin.pocketmon.data.User
import com.justin.pocketmon.data.source.PocketmonRepository
import com.justin.pocketmon.network.LoadApiStatus
import com.justin.pocketmon.util.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class LoginViewModel(private val stylishRepository: PocketmonRepository) : ViewModel() {

//    private val _user = MutableLiveData<User>()
//
//    val user: LiveData<User>
//        get() = _user

    val user = User()

    // Handle navigation to login success
    private val _navigateToLoginSuccess = MutableLiveData<User>()

    val navigateToLoginSuccess: LiveData<User>
        get() = _navigateToLoginSuccess

    // Handle leave login
    private val _loginFacebook = MutableLiveData<Boolean>()

    val loginFacebook: LiveData<Boolean>
        get() = _loginFacebook

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

//    lateinit var fbCallbackManager: CallbackManager

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
        Logger.i("[${this::class.simpleName}]$this")
        Logger.i("------------------------------------")
    }


    fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
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
            val googleIsExpired = googleSignInAccount.isExpired
            Logger.i("Google isExpired = $googleIsExpired")

            googleSignInAccount.idToken?.let { firebaseAuthWithGoogle(it) }

            user.name = googleSignInAccount.givenName + "  " + googleSignInAccount.familyName
            Logger.i("user.name = ${user.name}")
            user.email = googleSignInAccount.email.toString()
            Logger.i("user.email = ${user.email}")
            user.image = googleSignInAccount.photoUrl.toString()
            Logger.i("user.pictureUri = ${user.image}")

            UserManager.user = user


        } catch (e: ApiException) {
            // Sign in was unsuccessful
            Logger.e("Google log in failed code = ${e.statusCode}")
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)

        firebaseAuth = Firebase.auth

        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Logger.i("signInWithCredential:success")

                    val firebaseCurrentUser = firebaseAuth.currentUser
                    val firebaseTokenResult = firebaseCurrentUser?.getIdToken(false)?.result

                    user.id = firebaseCurrentUser?.uid.toString()
                    user.userToken = firebaseTokenResult?.token.toString()
                    Logger.i("~~~~~~開始~~~~~~firebaseTokenResult?.token.toString() = ${firebaseTokenResult?.token.toString()}")
//
//                    val firebaseDate = firebaseTokenResult?.expirationTimestamp?.let { Date(it) }
//                    Logger.i("firebaseDate = $firebaseDate")
//
//                    if (firebaseDate != null) {
//                        user.firebaseTokenExpiration = Timestamp(firebaseDate)
//                        Logger.i("user.firebaseTokenExpiration = ${user.firebaseTokenExpiration}")
//                    }
//
//                    user.signInProvider = firebaseTokenResult?.signInProvider.toString()
//                    Logger.i("firebaseDate = $firebaseDate")

                    UserManager.userToken = firebaseTokenResult?.token.toString()
                    Logger.i("firebaseTokenResult?.token.toString() = ${firebaseTokenResult?.token.toString()}")
                    Logger.i("UserManager.userToken = ${UserManager.userToken}")
//                    UserManager.user.value = user
//                    Logger.i("UserManager.user.value = ${UserManager.user.value}")


                    if (task.result.additionalUserInfo?.isNewUser == true) {

//                        userSignIn(user)
                    } else {
                        Logger.i("task.result.additionalUserInfo?.isNewUser == false")
                    }

                } else {
                    Logger.w("signInWithCredential:failure e = ${task.exception}")
                }
            }
    }

//    fun userSignIn(user: User) {
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//
//            when (val result = applicationRepository.userSignIn(user)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = MovieApplication.instance.getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }



    /**
     * track [StylishRepository.userSignIn]: -> [DefaultStylishRepository] : [StylishRepository] -> [StylishRemoteDataSource] : [StylishDataSource]
     * @param fbToken: Facebook token
     */
//    private fun loginStylish(fbToken: String) {
//
//        coroutineScope.launch {
//
//            _status.value = LoadApiStatus.LOADING
//            // It will return Result object after Deferred flow
//            when (val result = stylishRepository.userSignIn(fbToken)) {
//                is Result.Success -> {
//                    _error.value = null
//                    _status.value = LoadApiStatus.DONE
//                    UserManager.userToken = result.data.userSignIn?.accessToken
//                    _user.value = result.data.userSignIn?.user
//                    _navigateToLoginSuccess.value = user.value
//                }
//                is Result.Fail -> {
//                    _error.value = result.error
//                    _status.value = LoadApiStatus.ERROR
//                }
//                is Result.Error -> {
//                    _error.value = result.exception.toString()
//                    _status.value = LoadApiStatus.ERROR
//                }
//                else -> {
//                    _error.value = getString(R.string.you_know_nothing)
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        }
//    }
//
//    /**
//     * Login Stylish by Facebook: Step 1. Register FB Login Callback
//     */
//    fun login() {
//        _status.value = LoadApiStatus.LOADING
//
//        fbCallbackManager = CallbackManager.Factory.create()
//        LoginManager.getInstance().registerCallback(
//            fbCallbackManager,
//            object : FacebookCallback<LoginResult> {
//                override fun onSuccess(loginResult: LoginResult) {
//
//                    loginStylish(loginResult.accessToken.token)
//                }
//
//                override fun onCancel() { _status.value = LoadApiStatus.ERROR }
//
//                override fun onError(exception: FacebookException) {
//                    Logger.w("[${this::class.simpleName}] exception=${exception.message}")
//
//                    exception.message?.let {
//                        _error.value = if (it.contains("ERR_INTERNET_DISCONNECTED")) {
//                            getString(R.string.internet_not_connected)
//                        } else {
//                            it
//                        }
//                    }
//                    _status.value = LoadApiStatus.ERROR
//                }
//            }
//        )
//
//        loginFacebook()
//    }
//
//    /**
//     * Login Stylish by Facebook: Step 2. Login Facebook
//     */
//    private fun loginFacebook() {
//        _loginFacebook.value = true
//    }
//
    fun leaveLogin() {
        _leaveLogin.value = true
    }
//
//    fun onLeaveCompleted() {
//        _leave.value = null
//    }
//
//    fun nothing() {}
//
//    fun onLoginFacebookCompleted() {
//        _loginFacebook.value = null
//    }
}
