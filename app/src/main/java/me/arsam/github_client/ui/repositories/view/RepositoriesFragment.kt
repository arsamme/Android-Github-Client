package me.arsam.github_client.ui.repositories.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import me.arsam.github_client.databinding.FragmentRepositoriesBinding
import me.arsam.github_client.ui.repositories.adapters.RepositoryListAdapter

@AndroidEntryPoint
class RepositoriesFragment : Fragment() {
    private lateinit var binding: FragmentRepositoriesBinding
    private val repositoriesViewModel: RepositoriesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepositoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        val repositories = repositoriesViewModel.repositories.value ?: ArrayList()
        val adapter = RepositoryListAdapter(repositories)
        binding.rvRepositories.layoutManager =
            LinearLayoutManager(requireContext())
        binding.rvRepositories.adapter = adapter

        repositoriesViewModel.repositories.observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.repositories = it
                adapter.notifyDataSetChanged()
            }
        }
    }
}