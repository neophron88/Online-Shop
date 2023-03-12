package com.neophron.profile.presentation

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.neophron.feature.contract.common.DependencyProvider
import com.neophron.feature.contract.common.extractDependency
import com.neophron.feature.contract.main_feature.MainBottomNavHeightProvider
import com.neophron.feature.viewModelFactory.viewModelProvider
import com.neophron.mylibrary.ktx.fragment.findParentAs
import com.neophron.mylibrary.ktx.showToast
import com.neophron.mylibrary.viewbinding_delegate.viewBindings
import com.neophron.profile.R
import com.neophron.profile.databinding.ProfileFragmentBinding
import com.neophron.profile.di.ProfileAssistedFactoryProvider
import com.neophron.profile.presentation.helper.RealPathUtil
import java.io.File

class ProfileFragment : Fragment(R.layout.profile_fragment) {

    private val binding: ProfileFragmentBinding by viewBindings()

    private val viewModel: ProfileViewModel by viewModelProvider {
        findParentAs<DependencyProvider>()
            .extractDependency<ProfileAssistedFactoryProvider>()
            .getProfileFactory()
            .create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurePaddingRespectToBottomNav()
        setupNavigateUp()
        setupChangeAvatar()
        setupUserFeatures(binding)
        setupLogOut()

        observeUiState()
        observeUiEvent()
    }


    private fun setupNavigateUp() = binding.apply {
        navigateUp.setOnClickListener { findNavController().popBackStack() }
    }

    private fun configurePaddingRespectToBottomNav() {
        findParentAs<MainBottomNavHeightProvider>().setBottomNavHeightListener { height ->
            binding.profileContainer.updatePadding(bottom = height)
        }
    }

    private fun setupChangeAvatar() = binding.apply {
        changePhoto.setOnClickListener { requestAndGetPhoto() }
    }

    private fun requestAndGetPhoto() =
        requestReadWriteExternalStorage.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE)


    private fun setupLogOut() = binding.apply {
        logOutItem.root.setOnClickListener { viewModel.logOut() }
    }

    private fun observeUiState() =
        viewModel.uiState.observe(viewLifecycleOwner, ::updateUi)

    private fun updateUi(uiState: ProfileUiState) = binding.apply {
        val account = uiState.account ?: return@apply
        fullName.text = getString(R.string.full_name_pattern, account.firstName, account.lastName)
        if (uiState.changeAvatarInProgress)
            avatar.setImageDrawable(null)
        else
            account.avatarUrl?.let {
                Glide.with(this@ProfileFragment)
                    .load(File(it))
                    .into(avatar)
            }
    }

    private fun observeUiEvent() = viewModel.uiEvent.observe(viewLifecycleOwner) {
        if (it is ProfileUiEvent.ToastMessage) requireView().showToast(it.messageRes)
    }

    private val requestReadWriteExternalStorage =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { granted ->
            if (granted) {
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                photoPickActivity.launch(intent)
            }
        }

    private val photoPickActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                if (it == null) return@registerForActivityResult
                val intent = it.data ?: return@registerForActivityResult
                val uri = intent.data ?: return@registerForActivityResult
                val path = RealPathUtil.getRealPath(requireContext(), uri)
                Log.d("it0088", path)
                viewModel.changeAvatar(File(path))
            }
        }
}