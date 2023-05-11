package com.example.locationinbackground.main

import android.graphics.*
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.locationinbackground.LocationViewModel
import com.example.locationinbackground.LocationViewModelFactory
import com.example.locationinbackground.R
import com.example.locationinbackground.Router
import com.example.locationinbackground.data.PointMap
import com.example.locationinbackground.databinding.FragmentMapBinding
import com.example.locationinbackground.service.LocationApplication
import kotlin.math.max
import kotlin.math.min

class mapFragment : Fragment(){
    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!
    private val bitmap = Bitmap.createBitmap(710, 1200, Bitmap.Config.ARGB_8888)
    private val canvas = Canvas(bitmap)
    private val paint = Paint()
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
        binding.toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        paint.color = Color.RED
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
        paint.isAntiAlias = true

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.allPoints.observeForever {
            if (it.size > 2) {
                var startX = 0
                var startY = 0
                var stopX = 0
                var stopY = 0

                val senterX = 364
                val senterY = 440
                val baseNullX = it[0].location.toDouble() * 1000000 //37420940
                val baseNullY = it[0].latitude.toDouble() * 1000000 // 55867907

                for (i in 0..it.size - 2) {
                    startX = ((baseNullY - (it[i].latitude.toDouble() * 1000000)) / 24).toInt() + senterX
                    startY = (((it[i].location.toDouble() * 1000000) - baseNullX) / 24).toInt() + senterY
                    stopX =
                        ((baseNullY - (it[i + 1].latitude.toDouble() * 1000000)) / 24).toInt() + senterX
                    stopY =
                        (((it[i + 1].location.toDouble() * 1000000) - baseNullX) / 24).toInt() + senterY
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
        }
        binding.imageV.setImageBitmap(bitmap)

        binding.toolbar.setNavigationOnClickListener{
            Router.showMainFragmentMain(activity?.supportFragmentManager)
        }
    }
}