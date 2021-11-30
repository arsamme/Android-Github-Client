package me.arsam.github_client.ui.profile.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.arsam.github_client.data.models.User
import me.arsam.github_client.data.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(userRepository: UserRepository) :
    ViewModel() {

    val profileLiveData: LiveData<User> = userRepository.getProfile()
}