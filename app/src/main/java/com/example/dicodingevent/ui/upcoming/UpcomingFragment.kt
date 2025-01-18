package com.example.dicodingevent.ui.upcoming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dicodingevent.databinding.FragmentUpcomingBinding
import com.example.dicodingevent.ui.adapter.EventAdapter


class UpcomingFragment : Fragment() {

    private var _binding : FragmentUpcomingBinding? = null
    private val binding  get() = _binding!!
    private lateinit var mainAdapter: EventAdapter
    private val viewModel: UpcomingViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpcomingBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.isVisible = isLoading
        }

        viewModel.event.observe(viewLifecycleOwner){
            if (it.isNotEmpty()){
                mainAdapter.setData(it)
            }
        }
    }


}