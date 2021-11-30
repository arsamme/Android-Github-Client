package me.arsam.github_client.ui.repositories.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.data.repositories.RepoRepository
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(repoRepository: RepoRepository) :
    ViewModel() {

    val repositories: LiveData<List<Repository>> = repoRepository.getRepositories()
}