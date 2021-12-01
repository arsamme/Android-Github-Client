package me.arsam.github_client.ui.repositories.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import me.arsam.github_client.R
import me.arsam.github_client.data.Resource
import me.arsam.github_client.databinding.FragmentRepositoriesBinding
import me.arsam.github_client.ui.repositories.adapters.RepositoryDiffCallback
import me.arsam.github_client.ui.repositories.adapters.RepositoryListAdapter

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {
    private var _binding: FragmentRepositoriesBinding? = null
    private val binding get() = _binding!!

    private val repositoriesViewModel: RepositoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupViews() {
        val adapter = RepositoryListAdapter(RepositoryDiffCallback())
        binding.apply {
            repositoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext())
            repositoriesRecyclerView.adapter = adapter
        }

        repositoriesViewModel.repositoriesLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        errorTextView.visibility = View.GONE
                        repositoriesRecyclerView.visibility = View.VISIBLE
                    }

                    val repositories = it.data
                    adapter.submitList(repositories)
                }
                Resource.Empty -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        repositoriesRecyclerView.visibility = View.INVISIBLE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = resources.getString(R.string.failed_to_get_data)
                    }
                }
                is Resource.Error -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.GONE
                        repositoriesRecyclerView.visibility = View.INVISIBLE
                        errorTextView.visibility = View.VISIBLE
                        errorTextView.text = it.errorMessage
                    }
                }
                Resource.Loading -> {
                    binding.apply {
                        loadingProgressBar.visibility = View.VISIBLE
                        repositoriesRecyclerView.visibility = View.INVISIBLE
                        errorTextView.visibility = View.GONE
                    }
                }
            }
        }
    }
}