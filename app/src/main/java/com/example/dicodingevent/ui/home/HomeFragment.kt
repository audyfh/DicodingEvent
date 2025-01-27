package com.example.dicodingevent.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentHomeBinding
import com.example.dicodingevent.ui.adapter.EventAdapter
import com.example.dicodingevent.util.Result
import com.example.dicodingevent.util.ViewModelFactory


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding  get() = _binding!!
    private lateinit var adapter: BannerAdapter
    private lateinit var mainAdapter: EventAdapter
    private lateinit var viewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,ViewModelFactory.getInstance(requireContext()))[HomeViewModel::class.java]
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

        viewModel.upcomingEvent.observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Success -> {
                    adapter.setData(result.data.take(5))
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Empty -> {
                    binding.progressBar.isVisible = false
                    binding.tvError.isVisible = true
                }
                is Result.Error -> {
                    binding.progressBar.isVisible = false
                    binding.tvError.isVisible = true
                }
                else -> {
                    binding.progressBar.isVisible = false
                    binding.tvError.isVisible = true
                }
            }

        }

        viewModel.event.observe(viewLifecycleOwner){ result ->
            when(result){
                is Result.Success -> {
                    mainAdapter.setData(result.data.take(5))
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Empty -> {
                    binding.progressBar.isVisible = false
                    binding.tvError.isVisible = true
                }
                is Result.Error -> {
                    binding.tvError.isVisible = true
                }
                else -> {
                    binding.tvError.isVisible = true
                }
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