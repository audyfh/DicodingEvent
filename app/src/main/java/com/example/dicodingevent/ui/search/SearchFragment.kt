package com.example.dicodingevent.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentSearchBinding
import com.example.dicodingevent.ui.adapter.EventAdapter


class SearchFragment : Fragment() {

    private var _binding : FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel : SearchViewModel by viewModels()
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
        setupRecyclerView()
        val query = arguments?.let { SearchFragmentArgs.fromBundle(it).query}
        query?.let {
            viewModel.searchEvents(it)
        }

        observeSearchResults()
    }

    private fun observeSearchResults() {
        viewModel.searchResults.observe(viewLifecycleOwner) { results ->
            mainAdapter = EventAdapter()
            if (results.isNotEmpty()) {
                setupRecyclerView()
                mainAdapter.setData(results)
            } else {
                binding.tvNoResults.isVisible = true
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBarSearch.isVisible = isLoading
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