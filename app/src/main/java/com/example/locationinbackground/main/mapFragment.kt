package com.example.locationinbackground.main

import android.graphics.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.locationinbackground.LocationViewModel
import com.example.locationinbackground.LocationViewModelFactory
import com.example.locationinbackground.databinding.FragmentMapBinding
import com.example.locationinbackground.service.LocationApplication
import kotlin.math.max
import kotlin.math.min


class mapFragment : Fragment(){
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationViewModel by activityViewModels{
        LocationViewModelFactory(
            (activity?.application as LocationApplication).database.pointMapDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapBinding.inflate(inflater, container, false)

//        val bitmap = Bitmap.createBitmap(710, 1200, Bitmap.Config.ARGB_8888)
//        val canvas = Canvas(bitmap)
//        val paint = Paint()
//        paint.color = Color.RED
//        paint.style = Paint.Style.STROKE
//        paint.strokeWidth = 5F
//        paint.isAntiAlias = true
//
//        var startX = 0
//        var startY = 0
//        var stopX = 0
//        var stopY = 0
//        viewModel.allPoints.observeForever { listPoint ->
//            for(i in 0..listPoint.size-2 ){
//                startX = ((listPoint[i].latitude.toDouble() * 1000000)).toInt()
//                startY = ((listPoint[i].location.toDouble() * 1000000)).toInt()
//                stopX = ((listPoint[i+1].latitude.toDouble() * 1000000)).toInt()
//                stopY = ((listPoint[i+1].location.toDouble() * 1000000)).toInt()
//                canvas.drawLine(
//                    startX.toFloat(),
//                    startY.toFloat(),
//                    stopX.toFloat(),
//                    stopY.toFloat(),
//                    paint
//                )
//                startX = stopX
//                startY = stopY
//            }
//        }
//        binding.imageV.setImageBitmap(bitmap)
        // 55.849614 37.423785 НЛ
        // 55.867907 37.420940 ВЛ
        // 55.858891 37.436667 ВП
        // 55.851481 37.439142 НП

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}