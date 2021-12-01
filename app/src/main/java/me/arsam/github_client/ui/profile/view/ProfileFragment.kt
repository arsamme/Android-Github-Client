package me.arsam.github_client.ui.profile.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import me.arsam.github_client.R
import me.arsam.github_client.data.Resource
import me.arsam.github_client.databinding.FragmentProfileBinding

@AndroidEntryPoint
class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private val profileViewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        profileViewModel.profileLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    val profile = it.data

                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                        profileFragmentParent.visibility = View.VISIBLE
                        nameTextView.text = profile.name
                        emailTextView.text = profile.email
                        usernameTextView.text = profile.username
                        followersCountTextView.text = profile.followersCount.toString()
                        followingsCountTextView.text = profile.followingsCount.toString()
                        bioTextView.text = profile.bio
                        if (profile.avatarUrl != null) {
                            Glide
                                .with(this@ProfileFragment)
                                .load(profile.avatarUrl)
                                .circleCrop()
                                .into(avatarImageView)
                        }
                    }
                }
                Resource.Empty -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        profileFragmentParent.visibility = View.GONE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = resources.getString(R.string.failed_to_get_data)
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        profileFragmentParent.visibility = View.GONE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = it.errorMessage
                    }
                }
                Resource.Loading -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.VISIBLE
                        profileFragmentParent.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}