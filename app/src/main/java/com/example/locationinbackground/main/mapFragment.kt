package com.example.locationinbackground.main

import android.graphics.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.map
import com.example.locationinbackground.LocationViewModel
import com.example.locationinbackground.LocationViewModelFactory
import com.example.locationinbackground.databinding.FragmentMapBinding
import com.example.locationinbackground.service.LocationApplication


class mapFragment : Fragment() {
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels{
        LocationViewModelFactory(
            (activity?.application as LocationApplication).database.pointMapDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

        val bitmap = Bitmap.createBitmap(710, 1200, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint()
        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        paint.isAntiAlias = true

        var startX = 35
        var startY = 0
        var stopX = 0
        var stopY = 0
        var falag = true

        viewModel.allPoints.observeForever { listPoint ->
            for(i in 0..listPoint.size-2 ){
                startX = 60 + (((listPoint[i].latitude.toDouble() * 1000000) % 10000)/100).toInt()
                startY = 60 + (((listPoint[i].location.toDouble() * 1000000) % 10000)/100).toInt()
                stopX = 60 + (((listPoint[i+1].latitude.toDouble() * 1000000) % 10000)/100).toInt()
                stopY = 60 + (((listPoint[i+1].location.toDouble() * 1000000) % 10000)/100).toInt()
                canvas.drawLine(
                    startX.toFloat(),
                    startY.toFloat(),
                    stopX.toFloat(),
                    stopY.toFloat(),
                    paint
                )
                startX = stopX
                startY = stopY
            }
        }
        binding.imageV.setImageBitmap(bitmap)

        return binding.root
    }

}