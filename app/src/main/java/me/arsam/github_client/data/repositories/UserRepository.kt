package me.arsam.github_client.data.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.arsam.github_client.ProfileQuery
import me.arsam.github_client.data.db.dao.UserDao
import me.arsam.github_client.data.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val userDao: UserDao,
    private val apolloClient: ApolloClient
) {
    fun getProfile(): LiveData<User> {
        val profileLiveData: MutableLiveData<User> = MutableLiveData()

        CoroutineScope(Dispatchers.IO).launch {
            val localProfile = userDao.getProfile()
            withContext(Dispatchers.Main) {
                if (localProfile != null) {
                    profileLiveData.postValue(localProfile)
                }
            }

            try {
                val response = apolloClient.query(ProfileQuery()).await()
                val remoteProfile = response.data?.viewer
                Log.e("ars tag", remoteProfile.toString())
                if (remoteProfile != null) {
                    userDao.deleteAll()
                    val newProfile = User(
                        id = remoteProfile.id,
                        name = remoteProfile.name,
                        email = remoteProfile.email,
                        username = remoteProfile.login,
                        bio = remoteProfile.bio,
                        avatarUrl = remoteProfile.avatarUrl.toString(),
                        followersCount = remoteProfile.followers.totalCount,
                        followingsCount = remoteProfile.following.totalCount,
                    )
                    userDao.insert(newProfile)
                    withContext(Dispatchers.Main) {
                        profileLiveData.postValue(newProfile)
                    }
                }
            } catch (exception: Exception) {
                Log.e("apollo exp", exception.toString())
            }
        }

        return profileLiveData
    }
}