package me.arsam.github_client.data.repositories

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import me.arsam.github_client.data.Resource
import me.arsam.github_client.data.api.ApiResponse
import me.arsam.github_client.data.api.GithubApi
import me.arsam.github_client.data.db.dao.UserDao
import me.arsam.github_client.data.models.User
import me.arsam.github_client.utils.ConnectionUtils
import javax.inject.Inject


class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val githubApi: GithubApi,
    private val connectionUtils: ConnectionUtils
) {

    fun getProfile(): Flow<Resource<User>> {
        return object : NetworkBoundResource<User>() {
            override suspend fun saveNetworkResult(item: User) {
                withContext(Dispatchers.IO) {
                    userDao.deleteAll()
                    userDao.insert(item)
                }
            }

            override fun shouldFetch(data: User?): Boolean {
                return connectionUtils.isNetworkAvailable()
            }

            override suspend fun loadFromDb(): User? {
                return withContext(Dispatchers.IO) {
                    userDao.getProfile()
                }
            }

            override suspend fun fetchFromNetwork(): ApiResponse<User> {
                return withContext(Dispatchers.IO) {
                    githubApi.getProfile()
                }
            }

        }.asFlow()
    }
}