package me.arsam.github_client.data.api

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import me.arsam.github_client.ProfileQuery
import me.arsam.github_client.RepositoryListQuery
import me.arsam.github_client.data.models.Repository
import me.arsam.github_client.data.models.User
import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter
import javax.inject.Inject

class GithubApi @Inject constructor(val apolloClient: ApolloClient) {
    suspend fun getRepositories(): ApiResponse<List<Repository>> {
        try {
            val response = apolloClient.query(RepositoryListQuery()).await()
            val remoteRepos = response.data?.viewer?.repositories?.nodes?.filterNotNull()
                ?: return ApiEmptyResponse()

            val repositories: MutableList<Repository> = ArrayList()
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
                repositories.add(newRepo)
            }

            return ApiResponse.create(repositories)
        } catch (throwable: Throwable) {
            return ApiResponse.create(throwable);
        }
    }

    suspend fun getProfile(): ApiResponse<User> {
        try {
            val response = apolloClient.query(ProfileQuery()).await()
            val profile = response.data?.viewer ?: return ApiEmptyResponse()

            val user = User(
                id = profile.id,
                name = profile.name,
                email = profile.email,
                username = profile.login,
                bio = profile.bio,
                avatarUrl = profile.avatarUrl.toString(),
                followersCount = profile.followers.totalCount,
                followingsCount = profile.following.totalCount,
            )

            return ApiResponse.create(user)
        } catch (throwable: Throwable) {
            return ApiResponse.create(throwable);
        }
    }
}