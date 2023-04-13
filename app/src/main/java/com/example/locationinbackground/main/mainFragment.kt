package com.example.locationinbackground.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.locationinbackground.R
import com.example.locationinbackground.data.DataBase
import com.example.locationinbackground.databinding.FragmentMainBinding
import com.example.locationinbackground.service.LocationService
import java.security.Provider

class mainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val database = DataBase.getDatabase(view.context)
        binding.buttonStart.setOnClickListener{
            requireActivity().startService(Intent(view.context, LocationService::class.java))
        }
        binding.buttonStop.setOnClickListener{
            requireActivity().stopService(Intent(context, LocationService::class.java))
        }
    }
}