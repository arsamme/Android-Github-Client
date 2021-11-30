package me.arsam.github_client.ui.repositories.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.arsam.github_client.data.Resource
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.data.repositories.RepoRepository
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(private val repoRepository: RepoRepository) :
    ViewModel() {

    val repositoriesLiveData: MutableLiveData<Resource<List<Repository>>> = MutableLiveData()

    init {
        getRepositories()
    }


    fun getRepositories() {
        viewModelScope.launch {
            repoRepository.getRepositories().collect {
                repositoriesLiveData.postValue(it)
            }
        }
    }
}