package me.arsam.github_client.ui.profile.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.arsam.github_client.data.Resource
import me.arsam.github_client.data.models.User
import me.arsam.github_client.data.repositories.UserRepository
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository) :
    ViewModel() {

    val profileLiveData: MutableLiveData<Resource<User>> = MutableLiveData()

    init {
        getProfile()
    }


    fun getProfile() {
        viewModelScope.launch {
            userRepository.getProfile().collect {
                profileLiveData.postValue(it)
            }
        }
    }
}