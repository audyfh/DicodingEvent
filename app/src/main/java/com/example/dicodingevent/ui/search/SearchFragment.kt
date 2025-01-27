package com.example.dicodingevent.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentSearchBinding
import com.example.dicodingevent.ui.adapter.EventAdapter
import com.example.dicodingevent.util.Result
import com.example.dicodingevent.util.ViewModelFactory


class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SearchViewModel
    private lateinit var mainAdapter: EventAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentSearchBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext()))[SearchViewModel::class.java]
        setupRecyclerView()
        val query = arguments?.let { SearchFragmentArgs.fromBundle(it).query}
        query?.let {
            viewModel.searchEvents(it)
        }

        observeSearchResults()
    }

    private fun observeSearchResults() {
        viewModel.searchResults.observe(viewLifecycleOwner) { result ->
            when(result) {
                is Result.Success -> {
                    binding.progressBarSearch.isVisible = false
                    mainAdapter.setData(result.data)
                }
                is Result.Loading -> {
                    binding.progressBarSearch.isVisible = true
                }
                is Result.Empty -> {
                    binding.progressBarSearch.isVisible = false
                    binding.tvNoResults.isVisible = true
                }
                is Result.Error -> {
                    binding.progressBarSearch.isVisible = false
                    binding.tvNoResults.isVisible = true
                }
                else -> {
                    binding.progressBarSearch.isVisible = false
                    binding.tvNoResults.isVisible = true
                }
            }
        }
    }

    private fun setupRecyclerView(){
        mainAdapter = EventAdapter()
        binding.recyclerViewSearchResults.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@SearchFragment.mainAdapter
        }

        mainAdapter.setOnItemClickCallback {
            val action = SearchFragmentDirections.actionSearchFragmentToDetailActivity(it)
            findNavController().navigate(action)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}