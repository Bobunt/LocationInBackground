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

            canvas.drawLine(
                0.toFloat(),
                0.toFloat(),
                0.toFloat(),
                762.toFloat(),
                paint
            )
            canvas.drawLine(
                0.toFloat(),
                0.toFloat(),
                655.toFloat(),
                0.toFloat(),
                paint
            )
            canvas.drawLine(
                0.toFloat(),
                762.toFloat(),
                655.toFloat(),
                762.toFloat(),
                paint
            )
            canvas.drawLine(
                655.toFloat(),
                0.toFloat(),
                655.toFloat(),
                762.toFloat(),
                paint
            )

//        var listPoint = listOf<PointMap>(
////            PointMap(null,"55.851481","37.439142"),
//            PointMap(null,"55.857862","37.428673"),
//            PointMap(null,"55.856770","37.434385"),
//            PointMap(null,"55.853439","37.433733"),
//            PointMap(null,"55.852074","37.427311"),
//            PointMap(null,"55.855293","37.427100")
//        )

            binding.imageV.setImageBitmap(bitmap)
            // 55.849614 37.423785 НЛ
            // 55.867907 37.420940 ВЛ
            // 55.858891 37.436667 ВП
            // 55.851481 37.439142 НП
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)


            var startX = 0
            var startY = 0
            var stopX = 0
            var stopY = 0

//            var listPoint = listOf<PointMap>(
////            PointMap(null,"55.851481","37.439142"),
//                PointMap(null,"55.857862","37.428673"),
//                PointMap(null,"55.856770","37.434385"),
//                PointMap(null,"55.853439","37.433733"),
//                PointMap(null,"55.852074","37.427311"),
//                PointMap(null,"55.855293","37.427100")
//            )

            val baseNullX = 37420940
            val baseNullY = 55867907

//            for(i in 0..listPoint.size-2 ){
//                startX = ((( baseNullY - listPoint[i].latitude.toDouble() * 1000000))/24).toInt()
//                startY = (((listPoint[i].location.toDouble() * 1000000) - baseNullX)/24).toInt()
//                stopX = (((baseNullY - listPoint[i+1].latitude.toDouble() * 1000000))/24).toInt()
//                stopY = (((listPoint[i+1].location.toDouble() * 1000000) - baseNullX)/24).toInt()
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

            viewModel.allPoints.observeForever { listPoint ->
                for(i in 0..listPoint.size-2 ){
                startX = ((( baseNullY - listPoint[i].latitude.toDouble() * 1000000))/24).toInt()
                startY = (((listPoint[i].location.toDouble() * 1000000) - baseNullX)/24).toInt()
                stopX = (((baseNullY - listPoint[i+1].latitude.toDouble() * 1000000))/24).toInt()
                stopY = (((listPoint[i+1].location.toDouble() * 1000000) - baseNullX)/24).toInt()
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

            binding.toolbar.setNavigationOnClickListener{
                Router.showMainFragmentMain(activity?.supportFragmentManager)
            }
        }
    }