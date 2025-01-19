package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.ui.adapter.EventAdapter


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding  get() = _binding!!
    private lateinit var adapter: BannerAdapter
    private lateinit var mainAdapter: EventAdapter
    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeViewModel()
        setupSearchView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = BannerAdapter()
        mainAdapter = EventAdapter()

        binding.recyclerViewVertical.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@HomeFragment.mainAdapter
        }

        binding.recyclerViewHorizontal.apply {
            layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            setHasFixedSize(true)
            adapter = this@HomeFragment.adapter
        }

        mainAdapter.setOnItemClickCallback { event ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailActivity(event)
            findNavController().navigate(action)
        }

        adapter.setOnItemClickCallback { event ->
            val action = HomeFragmentDirections.actionHomeFragmentToDetailActivity(event)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.upcomingEvent.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                val maxList = it.take(5)
                adapter.setData(maxList)
            } else {
                binding.tvError.isVisible = true
            }
        }

        viewModel.event.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                val maxList = it.take(5)
                mainAdapter.setData(maxList)
            } else {
                binding.tvError.isVisible = true
            }
        }

    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    val action = HomeFragmentDirections.actionHomeFragmentToSearchFragment(it)
                    findNavController().navigate(action)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }


}