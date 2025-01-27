package com.example.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentUpcomingBinding
import com.example.dicodingevent.ui.adapter.EventAdapter
import com.example.dicodingevent.util.Result
import com.example.dicodingevent.util.ViewModelFactory


class UpcomingFragment : Fragment() {

    private var _binding : FragmentUpcomingBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainAdapter: EventAdapter
    private lateinit var viewModel: UpcomingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,ViewModelFactory.getInstance(requireContext()))[UpcomingViewModel::class.java]
        setupRecyclerView()
        observeViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView(){
        mainAdapter = EventAdapter()

        binding.recyclerViewUpcoming.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = this@UpcomingFragment.mainAdapter
        }

        mainAdapter.setOnItemClickCallback { event ->
            val action = UpcomingFragmentDirections.actionUpcomingFragmentToDetailActivity(event)
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
                    binding.progressBar.isVisible = false
                    binding.recyclerViewUpcoming.isVisible = false
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