package me.arsam.github_client.ui.repositories.view

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.data.repositories.RepoRepository
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(private val repoRepository: RepoRepository) :
    ViewModel() {

    val repositories: MutableLiveData<List<Repository>> = MutableLiveData()

    fun getRepos() {
        CoroutineScope(Dispatchers.IO).launch {
            val repos = repoRepository.getRepositories()
            withContext(Dispatchers.Main) {
                repos.observeForever {
                    repositories.postValue(it)
                }
            }
        }
    }
}