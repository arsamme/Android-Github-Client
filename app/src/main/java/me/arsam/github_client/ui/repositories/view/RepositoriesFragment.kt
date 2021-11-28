package me.arsam.github_client.ui.repositories.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import kotlinx.coroutines.channels.Channel
import me.arsam.github_client.Apollo
import me.arsam.github_client.RepositoryListQuery
import me.arsam.github_client.databinding.FragmentRepositoriesBinding
import me.arsam.github_client.ui.repositories.adapters.RepositoryListAdapter

class RepositoriesFragment : Fragment() {
    private lateinit var binding: FragmentRepositoriesBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val apollo = Apollo(requireContext());

        val repositories = mutableListOf<RepositoryListQuery.Node>()
        val adapter = RepositoryListAdapter(repositories)
        binding.repositoriesRecyclerView.layoutManager =
            LinearLayoutManager(requireContext())
        binding.repositoriesRecyclerView.adapter = adapter


        val channel = Channel<Unit>(Channel.CONFLATED)
        channel.trySend(Unit)
        adapter.onEndOfListReached = {
            channel.trySend(Unit)
        }

        lifecycleScope.launchWhenResumed {
            var cursor: String? = null
            for (item in channel) {
                val response = try {
                    apollo.apolloClient.query(
                        RepositoryListQuery(
                            10,
                            endCursor = Input.fromNullable(cursor)
                        )
                    ).await()
                } catch (e: ApolloException) {
                    Log.d("LaunchList", "Failure", e)
                    null
                }

                val newRepositories = response?.data?.viewer?.repositories?.nodes?.filterNotNull();

                if (newRepositories != null) {
                    repositories.addAll(newRepositories)
                    adapter.notifyDataSetChanged()
                }

                cursor = response?.data?.viewer?.repositories?.pageInfo?.endCursor;
                if (response?.data?.viewer?.repositories?.pageInfo?.hasNextPage != true) {
                    break
                }
            }

            adapter.onEndOfListReached = null
            channel.close()
        }
    }
}