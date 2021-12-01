package me.arsam.github_client.data.repositories

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.arsam.github_client.data.Resource
import me.arsam.github_client.data.api.ApiResponse
import me.arsam.github_client.data.api.GithubApi
import me.arsam.github_client.data.db.dao.RepositoryDao
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.utils.ConnectionUtils
import javax.inject.Inject

class RepoRepository @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val githubApi: GithubApi,
    private val connectionUtils: ConnectionUtils
) {
    fun getRepositories(): Flow<Resource<List<Repository>>> {
        return object : NetworkBoundResource<List<Repository>>() {
            override suspend fun saveNetworkResult(item: List<Repository>) {
                withContext(Dispatchers.IO) {
                    repositoryDao.deleteAll()
                    repositoryDao.insert(*item.toTypedArray())
                }
            }

            override fun shouldFetch(data: List<Repository>?): Boolean {
                return connectionUtils.isNetworkAvailable()
            }

            override suspend fun loadFromDb(): List<Repository>? {
                return withContext(Dispatchers.IO) {
                    repositoryDao.getAll()
                }
            }

            override suspend fun fetchFromNetwork(): ApiResponse<List<Repository>> {
                return withContext(Dispatchers.IO) {
                    githubApi.getRepositories()
                }
            }

        }.asFlow()
    }
}