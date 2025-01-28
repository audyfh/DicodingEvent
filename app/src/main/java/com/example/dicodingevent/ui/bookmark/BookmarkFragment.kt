package com.example.dicodingevent.ui.bookmark

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentBookmarkBinding
import com.example.dicodingevent.ui.adapter.EventAdapter
import com.example.dicodingevent.util.Result
import com.example.dicodingevent.util.ViewModelFactory


class BookmarkFragment : Fragment() {

    private var _binding : FragmentBookmarkBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainAdapter: EventAdapter
    private lateinit var viewModel : BookmarkViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookmarkBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,ViewModelFactory.getInstance(requireContext()))[BookmarkViewModel::class.java]
        observeViewModel()
        setupRecyclerView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        viewModel.getEvents()
    }

    private fun setupRecyclerView(){
        mainAdapter = EventAdapter()

        binding.recyclerViewFinished.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@BookmarkFragment.mainAdapter
        }

        mainAdapter.setOnItemClickCallback { event ->
            val action = BookmarkFragmentDirections.actionBookmarkFragmentToDetailActivity(event)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel(){
        viewModel.event.observe(viewLifecycleOwner){ result ->
            when(result) {
                is Result.Success -> {
                    binding.progressBar.isVisible = false
                    mainAdapter.setData(result.data)
                }
                is Result.Loading -> {
                    binding.progressBar.isVisible = true
                }
                is Result.Empty -> {
                    binding.recyclerViewFinished.isVisible = false
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


    }


}