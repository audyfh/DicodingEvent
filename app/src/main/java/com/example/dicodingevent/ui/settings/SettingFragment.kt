package com.example.dicodingevent.ui.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.dicodingevent.databinding.FragmentSettingBinding


class SettingFragment : Fragment() {

    private var _binding : FragmentSettingBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this,SettingViewModelFactory.getInstansce(requireContext()))[SettingViewModel::class.java]
        viewModel.darkMode.observe(viewLifecycleOwner){
            binding.switchTheme.isChecked = it
        }

        binding.switchTheme.setOnCheckedChangeListener{ _, isChecked ->
            viewModel.setDarkMode(isChecked)
        }
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            viewModel.setNotificationEnabled(isChecked)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}