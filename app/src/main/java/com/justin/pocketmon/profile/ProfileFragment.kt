package com.justin.pocketmon.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.justin.pocketmon.R
import com.justin.pocketmon.data.User
import com.justin.pocketmon.databinding.FragmentPlanBinding
import com.justin.pocketmon.databinding.FragmentProfileBinding
import com.justin.pocketmon.ext.getVmFactory
import com.justin.pocketmon.login.UserManager
import com.justin.pocketmon.login.UserManager.user
import com.justin.pocketmon.util.Logger
import java.nio.file.attribute.UserDefinedFileAttributeView

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> { getVmFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.user = UserManager.user


        Logger.i("binding.profileName.text = ${binding.profileName.text}")
        Logger.i("username profile = ${user.name}")

        return binding.root
    }
}