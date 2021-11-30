package me.arsam.github_client.ui.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import me.arsam.github_client.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.profileFragmentParent.visibility = ConstraintLayout.VISIBLE
                binding.txtName.text = it.name
                binding.txtEmail.text = it.email
                binding.txtUsername.text = it.username
                binding.txtFollowersCount.text = it.followersCount.toString()
                binding.txtFollowingsCount.text = it.followingsCount.toString()
                binding.txtBio.text = it.bio

                if (it.avatarUrl != null) {
                    Glide.with(this).load(it.avatarUrl).circleCrop().into(binding.imgAvatar)
                }
            }
        }
    }
}