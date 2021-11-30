package me.arsam.github_client.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.arsam.github_client.RepositoryListQuery
import me.arsam.github_client.data.db.dao.RepositoryDao
import me.arsam.github_client.data.models.Repository
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.collections.ArrayList

class RepoRepository @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val apolloClient: ApolloClient
) {
    fun getRepositories(): LiveData<List<Repository>> {
        val repositories: MutableLiveData<List<Repository>> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {
            val localRepos = repositoryDao.getAll()
            withContext(Dispatchers.Main) {
                repositories.postValue(localRepos)
            }

            try {
                val response = apolloClient.query(RepositoryListQuery()).await()
                val remoteRepos = response.data?.viewer?.repositories?.nodes?.filterNotNull()
                if (remoteRepos != null) {
                    repositoryDao.deleteAll()
                    val newRepositories: MutableList<Repository> = ArrayList()
                    for (repo in remoteRepos) {
                        val newRepo = Repository(
                            id = repo.id,
                            name = repo.name,
                            description = repo.description,
                            createdAt = LocalDateTime.parse(
                                repo.createdAt.toString(),
                                DateTimeFormatter.ISO_DATE_TIME,
                            ),
                            forksCount = repo.forkCount,
                            starsCount = repo.stargazerCount,
                            watchersCount = repo.watchers.totalCount,
                        )
                        repositoryDao.insert(newRepo)
                        newRepositories.add(newRepo)
                    }
                    withContext(Dispatchers.Main) {
                        repositories.postValue(newRepositories)
                    }
                }
            } catch (exception: Exception) {
                Log.e("apollo exp", exception.toString())
            }
        }



        return repositories
    }
}