package com.justin.pocketmon.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDialogFragment
import com.google.android.gms.auth.api.signin.GoogleSignIn
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.android.gms.common.api.ApiException
import com.justin.pocketmon.R
import com.justin.pocketmon.databinding.DialogLoginBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.login.UserManager.user
import com.justin.pocketmon.util.Logger
import java.lang.NullPointerException

class LoginDialog : AppCompatDialogFragment() {

    /**
     * Lazily initialize our [LoginViewModel].
     */
    private val viewModel by viewModels<LoginViewModel> { getVmFactory() }
    private lateinit var binding: DialogLoginBinding
    private lateinit var googleSignInClient: GoogleSignInClient

    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            Logger.d("有沒有進到googleSignIn ")
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            viewModel.handleSignInResult(task)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.LoginDialog)

        val googleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken("879844148049-6gp99r41tluqbre69kd77krn9fd906k9.apps.googleusercontent.com")
            .requestEmail()
            .build()

        googleSignInClient = context?.let { GoogleSignIn.getClient(it, googleSignInOptions) }
            ?: throw NullPointerException("Expression context?.let { GoogleSignIn.getClient(it, googleSignInOptions) } must not be null")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DialogLoginBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner

        binding.signInButton.setOnClickListener {
//            signInGoogle()
            signIn()
        }

        viewModel.leaveLogin.observe(
            viewLifecycleOwner,
            Observer {
                Logger.i("leave的值變化 -> $it")
                it?.let {
//                    if (it) findNavController().popBackStack()
                    dismiss()

                }
            }
        )

        return binding.root
    }


    private fun signIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken( "879844148049-6gp99r41tluqbre69kd77krn9fd906k9.apps.googleusercontent.com")
            .requestEmail()
            .build()

//        .requestIdToken("44039700708-qg19e235nofihjbsjkrv3efsklst64o8.apps.googleusercontent.com")

            googleSignInClient = context?.let { GoogleSignIn.getClient(it, gso) }!!
            signInGoogle()
//            val signInIntent = googleSignInClient?.signInIntent
//            startActivityForResult(signInIntent, RC_SIGN_IN)
        //...
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                Logger.i("try task.getResult Google sign in")
                val account = task.getResult(ApiException::class.java)
                val email = account?.email
                val token = account?.idToken

                Logger.i("givemepass , email:$email, token:$token")

                UserManager.userToken = token
                UserManager.userId = id.toString()
                UserManager.user.name = account?.displayName.toString()
                UserManager.user.image = account?.photoUrl.toString()
                Logger.d ("google profie is ${user.image}")
                Logger.d ("my name is ${user.name}")

//                user?.email = email
//                user?.name = account?.displayName
//                user?.let { viewModel.userSignIn(it) }
//                viewModel.userManager.value = UserManager
                Logger.i(" user.value $user")
                Logger.d("UserManager.userToken ${UserManager.userToken}")
//                Logger.d("UserManager.user ${user?.accountType}")
                Logger.d("user?.email ${user?.email}")
                Toast.makeText(context, "登入成功", Toast.LENGTH_SHORT).show()

            } catch (e: ApiException) {
                Logger.i("givemepass , signInResult:failed code=" + e.statusCode)
                Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
            }
        } else {
            Logger.i("givemepass login fail")
            Toast.makeText(context, "登入失敗", Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        const val RC_SIGN_IN = 100
    }

    private fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        launcher.launch(signInIntent)
//        dismiss()
    }

}